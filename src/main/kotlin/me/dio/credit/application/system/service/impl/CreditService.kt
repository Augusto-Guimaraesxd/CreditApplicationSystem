package me.dio.credit.application.system.service.impl

import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.repository.CreditRepository
import me.dio.credit.application.system.service.ICreditService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CreditService(
    private val crediteRepository : CreditRepository,
    private val customerService: CustomerService
): ICreditService {
    override fun save(credit: Credit): Credit {
        credit.apply {
            customer = customerService.findById(credit.customer?.id!!)
        }
        return this.crediteRepository.save(credit)
    }

    override fun findAllByCustomer(customerId: Long): List<Credit> =
        this.crediteRepository.findAllByCustomerId(customerId)



    override fun findCreditCode(customerId: Long, creditCode: UUID): Credit {
        val credit: Credit = (this.crediteRepository.findByCreditCode(creditCode)
            ?: throw RuntimeException("Creditcode $creditCode not found"))
        return if (credit.customer?.id == customerId)credit else throw RuntimeException("Contact admin")
    }
}