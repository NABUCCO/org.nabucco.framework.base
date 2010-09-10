/*
* Copyright 2010 PRODYNA AG
*
* Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.opensource.org/licenses/eclipse-1.0.php or
* http://www.nabucco-source.org/nabucco-license.html
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
* Generated with NABUCCO Generator 
*/
package org.nabucco.framework.base.facade.datatype.date;

import org.nabucco.framework.base.facade.datatype.Datatype;
import org.nabucco.framework.base.facade.datatype.NabuccoDatatype;
import org.nabucco.framework.base.facade.datatype.date.Day;
import org.nabucco.framework.base.facade.datatype.date.Hour;
import org.nabucco.framework.base.facade.datatype.date.Millisecond;
import org.nabucco.framework.base.facade.datatype.date.Month;
import org.nabucco.framework.base.facade.datatype.date.Second;
import org.nabucco.framework.base.facade.datatype.date.Year;
import org.nabucco.framework.base.facade.datatype.visitor.VisitorUtility;


/**
 * Calendar<p/>A container for a time specified by its fields<p/>
 *
 * @author Oliver Teichmann, PRODYNA AG, 2010-07-26
 */
public class Calendar extends NabuccoDatatype implements Datatype {

    private static final String[] CONSTRAINTS = { "l0,n;m1,1;", "l0,n;m1,1;", "l0,n;m1,1;",
            "l0,n;m1,1;", "l0,n;m1,1;", "l0,n;m1,1;", "l0,n;m1,1;" };

    private static final long serialVersionUID = 1L;

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

    /**
     * CloneObject.
     *
     * @param clone the Calendar.
     */
    protected void cloneObject(Calendar clone) {
        super.cloneObject(clone);
        if ((this.year != null)) {
            clone.year = this.year.cloneObject();
        }
        if ((this.month != null)) {
            clone.month = this.month.cloneObject();
        }
        if ((this.day != null)) {
            clone.day = this.day.cloneObject();
        }
        if ((this.hour != null)) {
            clone.hour = this.hour.cloneObject();
        }
        if ((this.minute != null)) {
            clone.minute = this.minute.cloneObject();
        }
        if ((this.second != null)) {
            clone.second = this.second.cloneObject();
        }
        if ((this.millisecond != null)) {
            clone.millisecond = this.millisecond.cloneObject();
        }
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

    @Override
    public Calendar cloneObject() {
        Calendar clone = new Calendar();
        this.cloneObject(clone);
        return clone;
    }

    @Override
    public void init() {
        this.initDefaults();
    }

    @Override
    public String[] getConstraints() {
        return CONSTRAINTS.clone();
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
    public String[] getPropertyNames() {
        return VisitorUtility.merge(super.getPropertyNames(), new String[] { "year", "month",
                "day", "hour", "minute", "second", "millisecond" });
    }

    @Override
    public Object[] getProperties() {
        return VisitorUtility.merge(
                super.getProperties(),
                new Object[] { this.getYear(), this.getMonth(), this.getDay(), this.getHour(),
                        this.getMinute(), this.getSecond(), this.getMillisecond() });
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
