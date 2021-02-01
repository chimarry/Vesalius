package pro.artse.dal.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class DateTimeUtil {
	public static Boolean isInRange(int numberOfDays, LocalDateTime since, LocalDateTime until) {
		LocalDate currentDate = LocalDate.now();
		LocalDate minDate = currentDate.minusDays(numberOfDays);
		return isInRange(minDate, currentDate, since.toLocalDate())
				|| isInRange(minDate, currentDate, until.toLocalDate());
	}

	public static Boolean areOverlapped(LocalDateTime firstSince, LocalDateTime firstUntil, LocalDateTime secondSince,
			LocalDateTime secondUntil, int pMinutes) {
		long timespanFirst = firstUntil.toEpochSecond(ZoneOffset.UTC) - firstSince.toEpochSecond(ZoneOffset.UTC);
		long timespanSecond = secondUntil.toEpochSecond(ZoneOffset.UTC) - secondSince.toEpochSecond(ZoneOffset.UTC);
		long difference = Math
				.abs(firstSince.toEpochSecond(ZoneOffset.UTC) - secondSince.toEpochSecond(ZoneOffset.UTC));
		if (firstSince.isAfter(secondSince)) {
			if (difference >= timespanSecond)
				return false;
			timespanSecond -= difference;
		} else {
			if (difference >= timespanFirst)
				return false;
			timespanFirst -= difference;
		}
		return Math.abs(timespanSecond - timespanFirst) >= (pMinutes * 60);
	}

	private static Boolean isInRange(LocalDate minValue, LocalDate maxValue, LocalDate valueToCheck) {
		return !(valueToCheck.isAfter(maxValue) || valueToCheck.isBefore(minValue));
	}
}