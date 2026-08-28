package org.unibl.etf.internship.intern_hub.model.dto;

import lombok.*;
import java.time.*;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CsvStudentDTO {
	@CsvBindByName(column = "username")
	private String username;
	
	@CsvBindByName(column = "email")
	private String email;
	
	@CsvBindByName(column = "password")
	private String password;
	
	@CsvBindByName(column = "firstName")
	private String firstName;
	
	@CsvBindByName(column = "lastName")
	private String lastName;
	
	@CsvBindByName(column = "studentIndex")
	private String studentIndex;
	
	@CsvBindByName(column = "dateOfBirth")
	@CsvDate("dd.MM.yyyy.")
	private LocalDate dateOfBirth;
	
	@CsvBindByName(column = "phoneNumber")
	private String phoneNumber;
}
