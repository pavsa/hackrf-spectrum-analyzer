/** @defgroup rtc_defines RTC Defines

@brief <b>Defined Constants and Types for the LPC43xx Real Time Clock (RTC)</b>

@ingroup LPC43xx_defines

@version 1.0.0

@author @htmlonly &copy; @endhtmlonly 2014 Jared Boone <jared@sharebrained.com>

@date 2 January 2014

LGPL License Terms @ref lgpl_license
 */
/*
 * This file is part of the libopencm3 project.
 *
 * Copyright (C) 2014 Jared Boone <jared@sharebrained.com>
 *
 * This library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library.  If not, see <http://www.gnu.org/licenses/>.
 */

#ifndef LPC43XX_RTC_H
#define LPC43XX_RTC_H

/**@{*/

#include <libopencm3/cm3/common.h>
#include <libopencm3/lpc43xx/memorymap.h>

#ifdef __cplusplus
extern "C" {
#endif

/* --- Convenience macros -------------------------------------------------- */

/* RTC port base address (for convenience) */
#define RTC                             RTC_BASE

/* --- RTC registers ------------------------------------------------------- */

#define RTC_ILR                         MMIO32(RTC_BASE + 0x000)
#define RTC_CCR                         MMIO32(RTC_BASE + 0x008)
#define RTC_CIIR                        MMIO32(RTC_BASE + 0x00c)
#define RTC_AMR                         MMIO32(RTC_BASE + 0x010)
#define RTC_CTIME0                      MMIO32(RTC_BASE + 0x014)
#define RTC_CTIME1                      MMIO32(RTC_BASE + 0x018)
#define RTC_CTIME2                      MMIO32(RTC_BASE + 0x01c)
#define RTC_SEC                         MMIO32(RTC_BASE + 0x020)
#define RTC_MIN                         MMIO32(RTC_BASE + 0x024)
#define RTC_HRS                         MMIO32(RTC_BASE + 0x028)
#define RTC_DOM                         MMIO32(RTC_BASE + 0x02c)
#define RTC_DOW                         MMIO32(RTC_BASE + 0x030)
#define RTC_DOY                         MMIO32(RTC_BASE + 0x034)
#define RTC_MONTH                       MMIO32(RTC_BASE + 0x038)
#define RTC_YEAR                        MMIO32(RTC_BASE + 0x03c)
#define RTC_CALIBRATION                 MMIO32(RTC_BASE + 0x040)
#define RTC_ASEC                        MMIO32(RTC_BASE + 0x060)
#define RTC_AMIN                        MMIO32(RTC_BASE + 0x064)
#define RTC_AHRS                        MMIO32(RTC_BASE + 0x068)
#define RTC_ADOM                        MMIO32(RTC_BASE + 0x06c)
#define RTC_ADOW                        MMIO32(RTC_BASE + 0x070)
#define RTC_ADOY                        MMIO32(RTC_BASE + 0x074)
#define RTC_AMON                        MMIO32(RTC_BASE + 0x078)
#define RTC_AYRS                        MMIO32(RTC_BASE + 0x07c)

/* --- RTC_ILR values ------------------------------------------- */

/* RTCCIF: Counter increment interrupt block interrupted */
#define RTC_ILR_RTCCIF_SHIFT (0)
#define RTC_ILR_RTCCIF_MASK (0x1 << RTC_ILR_RTCCIF_SHIFT)
#define RTC_ILR_RTCCIF(x) ((x) << RTC_ILR_RTCCIF_SHIFT)

/* RTCALF: Alarm interrupted */
#define RTC_ILR_RTCALF_SHIFT (1)
#define RTC_ILR_RTCALF_MASK (0x1 << RTC_ILR_RTCALF_SHIFT)
#define RTC_ILR_RTCALF(x) ((x) << RTC_ILR_RTCALF_SHIFT)

/* --- RTC_CCR values ------------------------------------------- */

/* CLKEN: Clock enable */
#define RTC_CCR_CLKEN_SHIFT (0)
#define RTC_CCR_CLKEN_MASK (0x1 << RTC_CCR_CLKEN_SHIFT)
#define RTC_CCR_CLKEN(x) ((x) << RTC_CCR_CLKEN_SHIFT)

/* CTCRST: CTC reset */
#define RTC_CCR_CTCRST_SHIFT (1)
#define RTC_CCR_CTCRST_MASK (0x1 << RTC_CCR_CTCRST_SHIFT)
#define RTC_CCR_CTCRST(x) ((x) << RTC_CCR_CTCRST_SHIFT)

/* CCALEN: Calibration counter enable */
#define RTC_CCR_CCALEN_SHIFT (4)
#define RTC_CCR_CCALEN_MASK (0x1 << RTC_CCR_CCALEN_SHIFT)
#define RTC_CCR_CCALEN(x) ((x) << RTC_CCR_CCALEN_SHIFT)

/* --- RTC_CIIR values ------------------------------------------ */

/* IMSEC: Second interrupt enable */
#define RTC_CIIR_IMSEC_SHIFT (0)
#define RTC_CIIR_IMSEC_MASK (0x1 << RTC_CIIR_IMSEC_SHIFT)
#define RTC_CIIR_IMSEC(x) ((x) << RTC_CIIR_IMSEC_SHIFT)

/* IMMIN: Minute interrupt enable */
#define RTC_CIIR_IMMIN_SHIFT (1)
#define RTC_CIIR_IMMIN_MASK (0x1 << RTC_CIIR_IMMIN_SHIFT)
#define RTC_CIIR_IMMIN(x) ((x) << RTC_CIIR_IMMIN_SHIFT)

/* IMHOUR: Hour interrupt enable */
#define RTC_CIIR_IMHOUR_SHIFT (2)
#define RTC_CIIR_IMHOUR_MASK (0x1 << RTC_CIIR_IMHOUR_SHIFT)
#define RTC_CIIR_IMHOUR(x) ((x) << RTC_CIIR_IMHOUR_SHIFT)

/* IMDOM: Day of month interrupt enable */
#define RTC_CIIR_IMDOM_SHIFT (3)
#define RTC_CIIR_IMDOM_MASK (0x1 << RTC_CIIR_IMDOM_SHIFT)
#define RTC_CIIR_IMDOM(x) ((x) << RTC_CIIR_IMDOM_SHIFT)

/* IMDOW: Day of week interrupt enable */
#define RTC_CIIR_IMDOW_SHIFT (4)
#define RTC_CIIR_IMDOW_MASK (0x1 << RTC_CIIR_IMDOW_SHIFT)
#define RTC_CIIR_IMDOW(x) ((x) << RTC_CIIR_IMDOW_SHIFT)

/* IMDOY: Day of year interrupt enable */
#define RTC_CIIR_IMDOY_SHIFT (5)
#define RTC_CIIR_IMDOY_MASK (0x1 << RTC_CIIR_IMDOY_SHIFT)
#define RTC_CIIR_IMDOY(x) ((x) << RTC_CIIR_IMDOY_SHIFT)

/* IMMON: Month interrupt enable */
#define RTC_CIIR_IMMON_SHIFT (6)
#define RTC_CIIR_IMMON_MASK (0x1 << RTC_CIIR_IMMON_SHIFT)
#define RTC_CIIR_IMMON(x) ((x) << RTC_CIIR_IMMON_SHIFT)

/* IMYEAR: Year interrupt enable */
#define RTC_CIIR_IMYEAR_SHIFT (7)
#define RTC_CIIR_IMYEAR_MASK (0x1 << RTC_CIIR_IMYEAR_SHIFT)
#define RTC_CIIR_IMYEAR(x) ((x) << RTC_CIIR_IMYEAR_SHIFT)

/* --- RTC_AMR values ------------------------------------------- */

/* AMRSEC: Second not compared for alarm */
#define RTC_AMR_AMRSEC_SHIFT (0)
#define RTC_AMR_AMRSEC_MASK (0x1 << RTC_AMR_AMRSEC_SHIFT)
#define RTC_AMR_AMRSEC(x) ((x) << RTC_AMR_AMRSEC_SHIFT)

/* AMRMIN: Minute not compared for alarm */
#define RTC_AMR_AMRMIN_SHIFT (1)
#define RTC_AMR_AMRMIN_MASK (0x1 << RTC_AMR_AMRMIN_SHIFT)
#define RTC_AMR_AMRMIN(x) ((x) << RTC_AMR_AMRMIN_SHIFT)

/* AMRHOUR: Hour not compared for alarm */
#define RTC_AMR_AMRHOUR_SHIFT (2)
#define RTC_AMR_AMRHOUR_MASK (0x1 << RTC_AMR_AMRHOUR_SHIFT)
#define RTC_AMR_AMRHOUR(x) ((x) << RTC_AMR_AMRHOUR_SHIFT)

/* AMRDOM: Day of month not compared for alarm */
#define RTC_AMR_AMRDOM_SHIFT (3)
#define RTC_AMR_AMRDOM_MASK (0x1 << RTC_AMR_AMRDOM_SHIFT)
#define RTC_AMR_AMRDOM(x) ((x) << RTC_AMR_AMRDOM_SHIFT)

/* AMRDOW: Day of week not compared for alarm */
#define RTC_AMR_AMRDOW_SHIFT (4)
#define RTC_AMR_AMRDOW_MASK (0x1 << RTC_AMR_AMRDOW_SHIFT)
#define RTC_AMR_AMRDOW(x) ((x) << RTC_AMR_AMRDOW_SHIFT)

/* AMRDOY: Day of year not compared for alarm */
#define RTC_AMR_AMRDOY_SHIFT (5)
#define RTC_AMR_AMRDOY_MASK (0x1 << RTC_AMR_AMRDOY_SHIFT)
#define RTC_AMR_AMRDOY(x) ((x) << RTC_AMR_AMRDOY_SHIFT)

/* AMRMON: Month not compared for alarm */
#define RTC_AMR_AMRMON_SHIFT (6)
#define RTC_AMR_AMRMON_MASK (0x1 << RTC_AMR_AMRMON_SHIFT)
#define RTC_AMR_AMRMON(x) ((x) << RTC_AMR_AMRMON_SHIFT)

/* AMRYEAR: Year not compared for alarm */
#define RTC_AMR_AMRYEAR_SHIFT (7)
#define RTC_AMR_AMRYEAR_MASK (0x1 << RTC_AMR_AMRYEAR_SHIFT)
#define RTC_AMR_AMRYEAR(x) ((x) << RTC_AMR_AMRYEAR_SHIFT)

/* --- RTC_CTIME0 values ---------------------------------------- */

/* SECONDS: Seconds */
#define RTC_CTIME0_SECONDS_SHIFT (0)
#define RTC_CTIME0_SECONDS_MASK (0x3f << RTC_CTIME0_SECONDS_SHIFT)
#define RTC_CTIME0_SECONDS(x) ((x) << RTC_CTIME0_SECONDS_SHIFT)

/* MINUTES: Minutes */
#define RTC_CTIME0_MINUTES_SHIFT (8)
#define RTC_CTIME0_MINUTES_MASK (0x3f << RTC_CTIME0_MINUTES_SHIFT)
#define RTC_CTIME0_MINUTES(x) ((x) << RTC_CTIME0_MINUTES_SHIFT)

/* HOURS: Hours */
#define RTC_CTIME0_HOURS_SHIFT (16)
#define RTC_CTIME0_HOURS_MASK (0x1f << RTC_CTIME0_HOURS_SHIFT)
#define RTC_CTIME0_HOURS(x) ((x) << RTC_CTIME0_HOURS_SHIFT)

/* DOW: Day of week */
#define RTC_CTIME0_DOW_SHIFT (24)
#define RTC_CTIME0_DOW_MASK (0x7 << RTC_CTIME0_DOW_SHIFT)
#define RTC_CTIME0_DOW(x) ((x) << RTC_CTIME0_DOW_SHIFT)

/* --- RTC_CTIME1 values ---------------------------------------- */

/* DOM: Day of month */
#define RTC_CTIME1_DOM_SHIFT (0)
#define RTC_CTIME1_DOM_MASK (0x1f << RTC_CTIME1_DOM_SHIFT)
#define RTC_CTIME1_DOM(x) ((x) << RTC_CTIME1_DOM_SHIFT)

/* MONTH: Month */
#define RTC_CTIME1_MONTH_SHIFT (8)
#define RTC_CTIME1_MONTH_MASK (0xf << RTC_CTIME1_MONTH_SHIFT)
#define RTC_CTIME1_MONTH(x) ((x) << RTC_CTIME1_MONTH_SHIFT)

/* YEAR: Year */
#define RTC_CTIME1_YEAR_SHIFT (16)
#define RTC_CTIME1_YEAR_MASK (0xfff << RTC_CTIME1_YEAR_SHIFT)
#define RTC_CTIME1_YEAR(x) ((x) << RTC_CTIME1_YEAR_SHIFT)

/* --- RTC_CTIME2 values ---------------------------------------- */

/* DOY: Day of year */
#define RTC_CTIME2_DOY_SHIFT (0)
#define RTC_CTIME2_DOY_MASK (0xfff << RTC_CTIME2_DOY_SHIFT)
#define RTC_CTIME2_DOY(x) ((x) << RTC_CTIME2_DOY_SHIFT)

/* --- RTC_SEC values ------------------------------------------- */

/* SECONDS: Seconds */
#define RTC_SEC_SECONDS_SHIFT (0)
#define RTC_SEC_SECONDS_MASK (0x3f << RTC_SEC_SECONDS_SHIFT)
#define RTC_SEC_SECONDS(x) ((x) << RTC_SEC_SECONDS_SHIFT)

/* --- RTC_MIN values ------------------------------------------- */

/* MINUTES: Minutes */
#define RTC_MIN_MINUTES_SHIFT (0)
#define RTC_MIN_MINUTES_MASK (0x3f << RTC_MIN_MINUTES_SHIFT)
#define RTC_MIN_MINUTES(x) ((x) << RTC_MIN_MINUTES_SHIFT)

/* --- RTC_HRS values ------------------------------------------- */

/* HOURS: Hours */
#define RTC_HRS_HOURS_SHIFT (0)
#define RTC_HRS_HOURS_MASK (0x1f << RTC_HRS_HOURS_SHIFT)
#define RTC_HRS_HOURS(x) ((x) << RTC_HRS_HOURS_SHIFT)

/* --- RTC_DOM values ------------------------------------------- */

/* DOM: Day of month */
#define RTC_DOM_DOM_SHIFT (0)
#define RTC_DOM_DOM_MASK (0x1f << RTC_DOM_DOM_SHIFT)
#define RTC_DOM_DOM(x) ((x) << RTC_DOM_DOM_SHIFT)

/* --- RTC_DOW values ------------------------------------------- */

/* DOW: Day of week */
#define RTC_DOW_DOW_SHIFT (0)
#define RTC_DOW_DOW_MASK (0x7 << RTC_DOW_DOW_SHIFT)
#define RTC_DOW_DOW(x) ((x) << RTC_DOW_DOW_SHIFT)

/* --- RTC_DOY values ------------------------------------------- */

/* DOY: Day of year */
#define RTC_DOY_DOY_SHIFT (0)
#define RTC_DOY_DOY_MASK (0x1ff << RTC_DOY_DOY_SHIFT)
#define RTC_DOY_DOY(x) ((x) << RTC_DOY_DOY_SHIFT)

/* --- RTC_MONTH values ----------------------------------------- */

/* MONTH: Month */
#define RTC_MONTH_MONTH_SHIFT (0)
#define RTC_MONTH_MONTH_MASK (0xf << RTC_MONTH_MONTH_SHIFT)
#define RTC_MONTH_MONTH(x) ((x) << RTC_MONTH_MONTH_SHIFT)

/* --- RTC_YEAR values ------------------------------------------ */

/* YEAR: Year */
#define RTC_YEAR_YEAR_SHIFT (0)
#define RTC_YEAR_YEAR_MASK (0xfff << RTC_YEAR_YEAR_SHIFT)
#define RTC_YEAR_YEAR(x) ((x) << RTC_YEAR_YEAR_SHIFT)

/* --- RTC_CALIBRATION values ----------------------------------- */

/* CALVAL: Calibration counter max */
#define RTC_CALIBRATION_CALVAL_SHIFT (0)
#define RTC_CALIBRATION_CALVAL_MASK (0x1ffff << RTC_CALIBRATION_CALVAL_SHIFT)
#define RTC_CALIBRATION_CALVAL(x) ((x) << RTC_CALIBRATION_CALVAL_SHIFT)

/* CALDIR: Calibration counter direction */
#define RTC_CALIBRATION_CALDIR_SHIFT (17)
#define RTC_CALIBRATION_CALDIR_MASK (0x1 << RTC_CALIBRATION_CALDIR_SHIFT)
#define RTC_CALIBRATION_CALDIR(x) ((x) << RTC_CALIBRATION_CALDIR_SHIFT)

/* --- RTC_ASEC values ------------------------------------------ */

/* SECONDS: Alarm seconds */
#define RTC_ASEC_SECONDS_SHIFT (0)
#define RTC_ASEC_SECONDS_MASK (0x3f << RTC_ASEC_SECONDS_SHIFT)
#define RTC_ASEC_SECONDS(x) ((x) << RTC_ASEC_SECONDS_SHIFT)

/* --- RTC_AMIN values ------------------------------------------ */

/* MINUTES: Alarm minutes */
#define RTC_AMIN_MINUTES_SHIFT (0)
#define RTC_AMIN_MINUTES_MASK (0x3f << RTC_AMIN_MINUTES_SHIFT)
#define RTC_AMIN_MINUTES(x) ((x) << RTC_AMIN_MINUTES_SHIFT)

/* --- RTC_AHRS values ------------------------------------------ */

/* HOURS: Alarm hours */
#define RTC_AHRS_HOURS_SHIFT (0)
#define RTC_AHRS_HOURS_MASK (0x1f << RTC_AHRS_HOURS_SHIFT)
#define RTC_AHRS_HOURS(x) ((x) << RTC_AHRS_HOURS_SHIFT)

/* --- RTC_ADOM values ------------------------------------------ */

/* DOM: Alarm day of month */
#define RTC_ADOM_DOM_SHIFT (0)
#define RTC_ADOM_DOM_MASK (0x1f << RTC_ADOM_DOM_SHIFT)
#define RTC_ADOM_DOM(x) ((x) << RTC_ADOM_DOM_SHIFT)

/* --- RTC_ADOW values ------------------------------------------ */

/* DOW: Alarm day of week */
#define RTC_ADOW_DOW_SHIFT (0)
#define RTC_ADOW_DOW_MASK (0x7 << RTC_ADOW_DOW_SHIFT)
#define RTC_ADOW_DOW(x) ((x) << RTC_ADOW_DOW_SHIFT)

/* --- RTC_ADOY values ------------------------------------------ */

/* DOY: Alarm day of year */
#define RTC_ADOY_DOY_SHIFT (0)
#define RTC_ADOY_DOY_MASK (0x1ff << RTC_ADOY_DOY_SHIFT)
#define RTC_ADOY_DOY(x) ((x) << RTC_ADOY_DOY_SHIFT)

/* --- RTC_AMON values ------------------------------------------ */

/* MONTH: Alarm month */
#define RTC_AMON_MONTH_SHIFT (0)
#define RTC_AMON_MONTH_MASK (0xf << RTC_AMON_MONTH_SHIFT)
#define RTC_AMON_MONTH(x) ((x) << RTC_AMON_MONTH_SHIFT)

/* --- RTC_AYRS values ------------------------------------------ */

/* YEAR: Alarm year */
#define RTC_AYRS_YEAR_SHIFT (0)
#define RTC_AYRS_YEAR_MASK (0xfff << RTC_AYRS_YEAR_SHIFT)
#define RTC_AYRS_YEAR(x) ((x) << RTC_AYRS_YEAR_SHIFT)

/**@}*/

#ifdef __cplusplus
}
#endif

#endif
