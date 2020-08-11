package com.MyFreelanceApp.MyApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MyFreelanceApp.MyApp.Model.Otp;

@Repository
public interface IOtpRepository extends JpaRepository <Otp,Long>
{
	
	Otp findByContactNo(long ContactNumber);
}
