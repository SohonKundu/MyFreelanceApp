package com.MyFreelanceApp.MyApp.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.Optional;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.MyFreelanceApp.MyApp.Model.Customer;
import com.MyFreelanceApp.MyApp.Model.Otp;
import com.MyFreelanceApp.MyApp.Repository.ICustomerRepository;
import com.MyFreelanceApp.MyApp.Repository.IOtpRepository;
import com.MyFreelanceApp.MyApp.Util.OtpGenerator;

@Service
public class CustomerService {

	@Autowired
	private ICustomerRepository customerRepository;

	@Autowired
	private IOtpRepository otpRepository;

	@Autowired
	private OtpGenerator otpGenerator;

	public Optional<Customer> GetByContactNo(long ContactNo, Optional<Integer> Otp) throws Exception {
		if (Otp.isPresent()) {
			Optional<Otp> otp = Optional.ofNullable(otpRepository.findByContactNo(ContactNo));
			if (otp.isPresent()) {
				if (otp.get().getOtp() == Otp.get()) {
					return Optional.ofNullable(customerRepository.findByContactNumber(ContactNo));
				} else
					throw new Exception("Otp not matched");
			} else
				throw new Exception("Otp not present");
		} else
			return Optional.ofNullable(customerRepository.findByContactNumber(ContactNo));

	}

	public long addCustomerInfo(Customer customer) {
		try {
			Optional<Customer> existingCustomer = Optional
					.ofNullable(customerRepository.findByContactNumber(customer.getContactNumber()));
			Customer c;
			if (existingCustomer.isPresent()) {
				c = existingCustomer.get();
				c.setContactNumber(customer.getContactNumber());
				c.setName(customer.getName());
				c.setAddress(customer.getAddress());
			} else {
				c = new Customer();
				c.setName(customer.getName());
				c.setContactNumber(customer.getContactNumber());
				c.setAddress(customer.getAddress());
			}
			
			customerRepository.saveAndFlush(c);
			return c.getId();
		} catch (Exception ex) {
			throw ex;
		}
	}

	public int SendOtp(Long number) throws ClientProtocolException, URISyntaxException, IOException {
		int otp = 0, f = 0;
		Optional<Otp> existingCustomerOtp = Optional.ofNullable(otpRepository.findByContactNo(number));
		if (existingCustomerOtp.isPresent()) {

			Calendar expiryTime = existingCustomerOtp.get().getCreatedOn();
			expiryTime.add(Calendar.MINUTE, 1);
			if (Calendar.getInstance().before(expiryTime)) {
				otp = existingCustomerOtp.get().getOtp();
				f = 1;
			} else
				otpRepository.delete(existingCustomerOtp.get());
		}
		if (f != 1) {

			otp = otpGenerator.createOtp();
			Otp newOtp = new Otp();
			newOtp.setOtp(otp);
			newOtp.setContactNo(number);
			newOtp.setCreatedOn(Calendar.getInstance());
			otpRepository.save(newOtp);
		}
		otpGenerator.SendOtp(number, otp);
		return otp;
	}

}
