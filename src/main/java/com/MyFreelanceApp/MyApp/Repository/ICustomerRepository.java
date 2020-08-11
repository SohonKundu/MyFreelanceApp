package com.MyFreelanceApp.MyApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.MyFreelanceApp.MyApp.Model.Customer;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer,Long>
{
	public Customer findByContactNumber(long ContactNo);

//	@Transactional
//	@Modifying
//	@Query("Update com.MyFreelanceApp.MyApp.Model.Customer c set c.otp= :otp"
//			+ " where c.contactNumber= :contactNumber" )
//	public void SaveOtp(@Param("otp")int otp, @Param("contactNumber")long contactNumber);
}
