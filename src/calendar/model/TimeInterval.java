package calendar.model;

import calendar.exception.InvalidEventTimeException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Обект (Value Object), представляващ времеви прозорец (интервал) с начален и краен час.
 * Гарантира, че крайният час винаги е след началния.
 */
public class TimeInterval {
    private final LocalTime startTime;
    private final LocalTime endTime;

    /**
     * Създава нов времеви интервал.
     *
     * @param startTime начален час
     * @param endTime   краен час
     * @throws InvalidEventTimeException ако крайният час е преди или равен на началния
     * @throws NullPointerException      ако подадените часове са null
     */
    public TimeInterval(LocalTime startTime, LocalTime endTime) {
        this.startTime = Objects.requireNonNull(startTime, "Starttime can't be null");
        this.endTime = Objects.requireNonNull(endTime, "Endtime can't be null");

        if (!this.endTime.isAfter(this.startTime)) {
            throw new InvalidEventTimeException("Error. Starttime has to be before endtime.");
        }
    }

    public LocalTime getStartTime() { return startTime; }
    public LocalTime getEndTime() { return endTime; }

    /**
     * Проверява дали този времеви интервал се застъпва с друг подаден интервал.
     *
     * @param other интервалът, с който се прави сравнението
     * @return {@code true} ако има застъпване, иначе {@code false}
     */
    public boolean overlapsWith(TimeInterval other) {
        return this.startTime.isBefore(other.endTime) && this.endTime.isAfter(other.startTime);
    }

    /**
     * Пресмята и връща общата продължителност на интервала в минути.
     *
     * @return продължителността в минути
     */
    public long getDurationMinutes() {
        return ChronoUnit.MINUTES.between(startTime, endTime);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TimeInterval that)) return false;
        return startTime.equals(that.startTime) && endTime.equals(that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }

    @Override
    public String toString() {
        return startTime + " - " + endTime;
    }
}
