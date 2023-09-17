package com.triply.fleetmanagement.service.impl

import com.triply.fleetmanagement.domain.Company
import com.triply.fleetmanagement.repository.CompanyRepository
import com.triply.fleetmanagement.service.dto.CompanyDto
import com.triply.fleetmanagement.service.dto.toEntity
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class CompanyServiceImplTest {

    @Test
    fun testSaveCompany() {
        val companyRepository = mock(CompanyRepository::class.java)
        val companyService = CompanyServiceImpl(companyRepository)
        val companyDto = CompanyDto(companyId = 1L, companyName = "TestCompany")

        `when`(companyRepository.save(any(Company::class.java))).thenReturn(companyDto.toEntity())

        val savedCompany = companyService.save(companyDto)

        verify(companyRepository, times(1)).save(any(Company::class.java))
        assertEquals(companyDto, savedCompany)
    }

    @Test
    fun testFindAllCompanies() {
        val companyRepository = mock(CompanyRepository::class.java)
        val companyService = CompanyServiceImpl(companyRepository)
        val company1 = Company(id = 1L, name = "Company1")
        val company2 = Company(id = 2L, name = "Company2")
        val companyList = listOf(company1, company2)

        `when`(companyRepository.findAll()).thenReturn(companyList)

        val allCompanies = companyService.findAll()

        verify(companyRepository, times(1)).findAll()
        assertEquals(2, allCompanies.size)
        assertEquals("Company1", allCompanies[0].companyName)
        assertEquals("Company2", allCompanies[1].companyName)
    }

    @Test
    fun testFindCompanyById() {
        val companyRepository = mock(CompanyRepository::class.java)
        val companyService = CompanyServiceImpl(companyRepository)
        val companyId = 1L
        val company = Company(id = companyId, name = "TestCompany")

        `when`(companyRepository.findById(companyId)).thenReturn(Optional.of(company))

        val optionalCompany = companyService.findOne(companyId)

        verify(companyRepository, times(1)).findById(companyId)
        assertTrue(optionalCompany.isPresent)
        assertEquals("TestCompany", optionalCompany.get().name)
    }
}
