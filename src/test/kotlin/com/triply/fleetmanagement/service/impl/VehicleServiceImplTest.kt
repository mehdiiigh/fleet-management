import com.triply.fleetmanagement.domain.Company
import com.triply.fleetmanagement.domain.Employee
import com.triply.fleetmanagement.domain.Vehicle
import com.triply.fleetmanagement.domain.VehicleUsage
import com.triply.fleetmanagement.repository.CompanyRepository
import com.triply.fleetmanagement.repository.EmployeeRepository
import com.triply.fleetmanagement.repository.VehicleRepository
import com.triply.fleetmanagement.repository.VehicleUsageRepository
import com.triply.fleetmanagement.service.impl.VehicleServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.mock.web.MockMultipartFile
import java.util.*

class VehicleServiceImplTest {

    private lateinit var vehicleService: VehicleServiceImpl
    @Mock
    private lateinit var companyRepository: CompanyRepository
    @Mock
    private lateinit var vehicleRepository: VehicleRepository
    @Mock
    private lateinit var vehicleUsageRepository: VehicleUsageRepository
    @Mock
    private lateinit var employeeRepository: EmployeeRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        vehicleService = VehicleServiceImpl(vehicleUsageRepository, vehicleRepository, employeeRepository, companyRepository)
    }

    @Test
    fun insertVehicleToDB() {
        val companyId = 1L
        val csvData = "vehicleType,otherColumn\nCar,Value1\nBike,Value2\n"
        val file = MockMultipartFile("file.csv", csvData.toByteArray())

        val company = Company(id = 1, name = "triply")
        `when`(companyRepository.findById(companyId)).thenReturn(Optional.of(company))

        val savedVehicles = mutableListOf<Vehicle>()
        doAnswer { invocation ->
            val vehicle = invocation.getArgument<Vehicle>(0)
            savedVehicles.add(vehicle)
            vehicle
        }.`when`(vehicleRepository).save(any())

        val insertedCount = vehicleService.insertVehicleToDB(companyId, file)

        assertEquals(2, insertedCount) // Ensure that two vehicles were inserted
        assertEquals(2, savedVehicles.size) // Ensure that two vehicles were saved
        verify(companyRepository).findById(companyId) // Verify that findById was called
        verify(vehicleRepository, times(2)).save(any()) // Verify that save was called twice
    }


    @Test
    fun testInsertVehicleUsageCSVToDB() {
        val averageWeeklyMileage = 1000L
        val vehicleType = "Car"
        val employeeId = 1L
        val employeeIdNotInDB = 2L
        val file1Content = "average_weekly_mileage,vehicle_type,employee_id\n$averageWeeklyMileage,$vehicleType,$employeeId\n"
        val file2Content = "average_weekly_mileage,vehicle_type,employee_id\n$averageWeeklyMileage,$vehicleType,$employeeIdNotInDB\n"

        val company = Company(id = 1, name = "triply")
        val vehicle = Vehicle(type = vehicleType, emissionFactors = 2.5, company = company)
        val employee = Employee(id = employeeId, company = company)
        val vehicleUsage1 = VehicleUsage(
                vehicle = vehicle,
                employee = employee,
                averageWeeklyMileage = 100.0
        )

        `when`(vehicleRepository.findById(vehicleType)).thenReturn(Optional.of(vehicle))
        `when`(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee))
        `when`(employeeRepository.findById(employeeIdNotInDB)).thenReturn(Optional.empty())
        `when`(vehicleUsageRepository.save(any(VehicleUsage::class.java))).thenReturn(vehicleUsage1)

        val file1 = MockMultipartFile("test1.csv", "test1.csv", "text/csv", file1Content.toByteArray())
        val file2 = MockMultipartFile("test2.csv", "test2.csv", "text/csv", file2Content.toByteArray())

        val result = vehicleService.insertVehicleUsageCSVToDB(file1)
        val resultNotFound = vehicleService.insertVehicleUsageCSVToDB(file2)

        assertEquals(1, result)
        assertEquals(0, resultNotFound)
    }
}
