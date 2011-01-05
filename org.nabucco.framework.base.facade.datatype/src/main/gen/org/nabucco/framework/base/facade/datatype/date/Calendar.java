/*
 * NABUCCO Generator, Copyright (c) 2010, PRODYNA AG, Germany. All rights reserved.
 */
package org.nabucco.framework.base.facade.datatype.date;

import java.util.List;
import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.date.Day;
import org.nabucco.framework.base.facade.datatype.date.Hour;
import org.nabucco.framework.base.facade.datatype.date.Millisecond;
import org.nabucco.framework.base.facade.datatype.date.Minute;
import org.nabucco.framework.base.facade.datatype.date.Month;
import org.nabucco.framework.base.facade.datatype.date.Second;
import org.nabucco.framework.base.facade.datatype.date.Year;
import org.nabucco.framework.base.facade.datatype.property.BasetypeProperty;
import org.nabucco.framework.base.facade.datatype.property.NabuccoProperty;

/**
 * Calendar<p/>A container for a time specified by its fields<p/>
 *
 * @author Oliver Teichmann, PRODYNA AG, 2010-07-26
 */
public class Calendar extends NabuccoDatatype implements Datatype {

    private static final long serialVersionUID = 1L;

    private static final String[] PROPERTY_NAMES = { "year", "month", "day", "hour", "minute",
            "second", "millisecond" };

    private static final String[] PROPERTY_CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;",
            "l0,n;m1,1;", "l0,n;m1,1;", "l0,n;m1,1;", "l0,n;m1,1;", "l0,n;m1,1;" };

    /** The year of the represented time */
    private Year year;

    /** The month of the represented time */
    private Month month;

    /** The day of the month of the represented time */
    private Day day;

    /** The hour of day of the represented time */
    private Hour hour;

    /** The minute of the represented time */
    private Minute minute;

    /** The second of the represented time */
    private Second second;

    /** The millisecond of the represented time */
    private Millisecond millisecond;

    /** Constructs a new Calendar instance. */
    public Calendar() {
        super();
        this.initDefaults();
    }

    /** InitDefaults. */
    private void initDefaults() {
        year = new Year(1900);
        month = new Month(1);
        day = new Day(1);
        hour = new Hour(0);
        minute = new Minute(0);
        second = new Second(0);
        millisecond = new Millisecond(0);
    }

    /**
     * CloneObject.
     *
     * @param clone the Calendar.
     */
    protected void cloneObject(Calendar clone) {
        super.cloneObject(clone);
        if ((this.getYear() != null)) {
            clone.setYear(this.getYear().cloneObject());
        }
        if ((this.getMonth() != null)) {
            clone.setMonth(this.getMonth().cloneObject());
        }
        if ((this.getDay() != null)) {
            clone.setDay(this.getDay().cloneObject());
        }
        if ((this.getHour() != null)) {
            clone.setHour(this.getHour().cloneObject());
        }
        if ((this.getMinute() != null)) {
            clone.setMinute(this.getMinute().cloneObject());
        }
        if ((this.getSecond() != null)) {
            clone.setSecond(this.getSecond().cloneObject());
        }
        if ((this.getMillisecond() != null)) {
            clone.setMillisecond(this.getMillisecond().cloneObject());
        }
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public List<NabuccoProperty<?>> getProperties() {
        List<NabuccoProperty<?>> properties = super.getProperties();
        properties.add(new BasetypeProperty<Year>(PROPERTY_NAMES[0], Year.class,
                PROPERTY_CONSTRAINTS[0], this.year));
        properties.add(new BasetypeProperty<Month>(PROPERTY_NAMES[1], Month.class,
                PROPERTY_CONSTRAINTS[1], this.month));
        properties.add(new BasetypeProperty<Day>(PROPERTY_NAMES[2], Day.class,
                PROPERTY_CONSTRAINTS[2], this.day));
        properties.add(new BasetypeProperty<Hour>(PROPERTY_NAMES[3], Hour.class,
                PROPERTY_CONSTRAINTS[3], this.hour));
        properties.add(new BasetypeProperty<Minute>(PROPERTY_NAMES[4], Minute.class,
                PROPERTY_CONSTRAINTS[4], this.minute));
        properties.add(new BasetypeProperty<Second>(PROPERTY_NAMES[5], Second.class,
                PROPERTY_CONSTRAINTS[5], this.second));
        properties.add(new BasetypeProperty<Millisecond>(PROPERTY_NAMES[6], Millisecond.class,
                PROPERTY_CONSTRAINTS[6], this.millisecond));
        return properties;
    }

    @Override
    public boolean equals(Object obj) {
        if ((this == obj)) {
            return true;
        }
        if ((obj == null)) {
            return false;
        }
        if ((this.getClass() != obj.getClass())) {
            return false;
        }
        if ((!super.equals(obj))) {
            return false;
        }
        final Calendar other = ((Calendar) obj);
        if ((this.year == null)) {
            if ((other.year != null))
                return false;
        } else if ((!this.year.equals(other.year)))
            return false;
        if ((this.month == null)) {
            if ((other.month != null))
                return false;
        } else if ((!this.month.equals(other.month)))
            return false;
        if ((this.day == null)) {
            if ((other.day != null))
                return false;
        } else if ((!this.day.equals(other.day)))
            return false;
        if ((this.hour == null)) {
            if ((other.hour != null))
                return false;
        } else if ((!this.hour.equals(other.hour)))
            return false;
        if ((this.minute == null)) {
            if ((other.minute != null))
                return false;
        } else if ((!this.minute.equals(other.minute)))
            return false;
        if ((this.second == null)) {
            if ((other.second != null))
                return false;
        } else if ((!this.second.equals(other.second)))
            return false;
        if ((this.millisecond == null)) {
            if ((other.millisecond != null))
                return false;
        } else if ((!this.millisecond.equals(other.millisecond)))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = super.hashCode();
        result = ((PRIME * result) + ((this.year == null) ? 0 : this.year.hashCode()));
        result = ((PRIME * result) + ((this.month == null) ? 0 : this.month.hashCode()));
        result = ((PRIME * result) + ((this.day == null) ? 0 : this.day.hashCode()));
        result = ((PRIME * result) + ((this.hour == null) ? 0 : this.hour.hashCode()));
        result = ((PRIME * result) + ((this.minute == null) ? 0 : this.minute.hashCode()));
        result = ((PRIME * result) + ((this.second == null) ? 0 : this.second.hashCode()));
        result = ((PRIME * result) + ((this.millisecond == null) ? 0 : this.millisecond.hashCode()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder appendable = new StringBuilder();
        appendable.append("<Calendar>\n");
        appendable.append(super.toString());
        appendable.append((("<year>" + this.year) + "</year>\n"));
        appendable.append((("<month>" + this.month) + "</month>\n"));
        appendable.append((("<day>" + this.day) + "</day>\n"));
        appendable.append((("<hour>" + this.hour) + "</hour>\n"));
        appendable.append((("<minute>" + this.minute) + "</minute>\n"));
        appendable.append((("<second>" + this.second) + "</second>\n"));
        appendable.append((("<millisecond>" + this.millisecond) + "</millisecond>\n"));
        appendable.append("</Calendar>\n");
        return appendable.toString();
    }

    @Override
    public Calendar cloneObject() {
        Calendar clone = new Calendar();
        this.cloneObject(clone);
        return clone;
    }

    /**
     * The year of the represented time
     *
     * @return the Year.
     */
    public Year getYear() {
        return this.year;
    }

    /**
     * The year of the represented time
     *
     * @param year the Year.
     */
    public void setYear(Year year) {
        this.year = year;
    }

    /**
     * The year of the represented time
     *
     * @param year the Integer.
     */
    public void setYear(Integer year) {
        if ((this.year == null)) {
            this.year = new Year();
        }
        this.year.setValue(year);
    }

    /**
     * The month of the represented time
     *
     * @return the Month.
     */
    public Month getMonth() {
        return this.month;
    }

    /**
     * The month of the represented time
     *
     * @param month the Month.
     */
    public void setMonth(Month month) {
        this.month = month;
    }

    /**
     * The month of the represented time
     *
     * @param month the Integer.
     */
    public void setMonth(Integer month) {
        if ((this.month == null)) {
            this.month = new Month();
        }
        this.month.setValue(month);
    }

    /**
     * The day of the month of the represented time
     *
     * @return the Day.
     */
    public Day getDay() {
        return this.day;
    }

    /**
     * The day of the month of the represented time
     *
     * @param day the Day.
     */
    public void setDay(Day day) {
        this.day = day;
    }

    /**
     * The day of the month of the represented time
     *
     * @param day the Integer.
     */
    public void setDay(Integer day) {
        if ((this.day == null)) {
            this.day = new Day();
        }
        this.day.setValue(day);
    }

    /**
     * The hour of day of the represented time
     *
     * @return the Hour.
     */
    public Hour getHour() {
        return this.hour;
    }

    /**
     * The hour of day of the represented time
     *
     * @param hour the Hour.
     */
    public void setHour(Hour hour) {
        this.hour = hour;
    }

    /**
     * The hour of day of the represented time
     *
     * @param hour the Integer.
     */
    public void setHour(Integer hour) {
        if ((this.hour == null)) {
            this.hour = new Hour();
        }
        this.hour.setValue(hour);
    }

    /**
     * The minute of the represented time
     *
     * @return the Minute.
     */
    public Minute getMinute() {
        return this.minute;
    }

    /**
     * The minute of the represented time
     *
     * @param minute the Minute.
     */
    public void setMinute(Minute minute) {
        this.minute = minute;
    }

    /**
     * The minute of the represented time
     *
     * @param minute the Integer.
     */
    public void setMinute(Integer minute) {
        if ((this.minute == null)) {
            this.minute = new Minute();
        }
        this.minute.setValue(minute);
    }

    /**
     * The second of the represented time
     *
     * @return the Second.
     */
    public Second getSecond() {
        return this.second;
    }

    /**
     * The second of the represented time
     *
     * @param second the Second.
     */
    public void setSecond(Second second) {
        this.second = second;
    }

    /**
     * The second of the represented time
     *
     * @param second the Integer.
     */
    public void setSecond(Integer second) {
        if ((this.second == null)) {
            this.second = new Second();
        }
        this.second.setValue(second);
    }

    /**
     * The millisecond of the represented time
     *
     * @return the Millisecond.
     */
    public Millisecond getMillisecond() {
        return this.millisecond;
    }

    /**
     * The millisecond of the represented time
     *
     * @param millisecond the Millisecond.
     */
    public void setMillisecond(Millisecond millisecond) {
        this.millisecond = millisecond;
    }

    /**
     * The millisecond of the represented time
     *
     * @param millisecond the Integer.
     */
    public void setMillisecond(Integer millisecond) {
        if ((this.millisecond == null)) {
            this.millisecond = new Millisecond();
        }
        this.millisecond.setValue(millisecond);
    }
}
