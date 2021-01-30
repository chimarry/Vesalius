package pro.artse.dal.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class DateTimeUtil {
	public static Boolean isInRange(int numberOfDays, LocalDateTime since, LocalDateTime until) {
		LocalDate currentDate = LocalDate.now();
		LocalDate maxDate = currentDate.plusDays(numberOfDays);
		return !notInRange(currentDate, maxDate, since.toLocalDate())
				|| !notInRange(currentDate, maxDate, until.toLocalDate());
	}

	private static Boolean notInRange(LocalDate minValue, LocalDate maxValue, LocalDate valueToCheck) {
		return !(valueToCheck.isAfter(maxValue) || valueToCheck.isBefore(minValue));
	}
}
