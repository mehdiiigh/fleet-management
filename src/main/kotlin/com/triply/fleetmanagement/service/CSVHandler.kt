package com.triply.fleetmanagement.service

import com.opencsv.CSVReader
import com.opencsv.bean.CsvToBean
import com.opencsv.bean.MappingStrategy
import java.io.InputStream
import java.io.InputStreamReader

class CSVHandler<T>(private val mappingStrategy: MappingStrategy<T>) {

    fun parseCsv(inputStream: InputStream): List<T>{
        var result = emptyList<T>()
        try
        {
            val reader = CSVReader(InputStreamReader(inputStream))
            val csvToBean: CsvToBean<T> = CsvToBean<T>()
            csvToBean.setCsvReader(reader)
            csvToBean.setMappingStrategy(mappingStrategy)
            csvToBean.setIgnoreEmptyLines(true)
            csvToBean.setThrowExceptions(false)
            result = csvToBean.parse()
        } catch (_: Throwable){
        }
        return result
    }
}