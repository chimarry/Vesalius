package pro.artse.dal.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class DateTimeUtil {
	public static Boolean isInRange(int numberOfDays, LocalDateTime since, LocalDateTime until) {
		LocalDate currentDate = LocalDate.now();
		LocalDate minDate = currentDate.minusDays(numberOfDays);
		return isInRange(minDate, currentDate, since.toLocalDate())
				|| isInRange(minDate, currentDate, until.toLocalDate());
	}

	private static Boolean isInRange(LocalDate minValue, LocalDate maxValue, LocalDate valueToCheck) {
		return !(valueToCheck.isAfter(maxValue) || valueToCheck.isBefore(minValue));
	}
}