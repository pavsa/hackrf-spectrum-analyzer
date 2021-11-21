/** @defgroup ritimer_defines Repetitive Interrupt Timer Defines

@brief <b>Defined Constants and Types for the LPC43xx Repetitive Interrupt
Timer</b>

@ingroup LPC43xx_defines

@version 1.0.0

@author @htmlonly &copy; @endhtmlonly 2012 Michael Ossmann <mike@ossmann.com>
@author @htmlonly &copy; @endhtmlonly 2014 Jared Boone <jared@sharebrained.com>

@date 10 March 2013

LGPL License Terms @ref lgpl_license
 */
/*
 * This file is part of the libopencm3 project.
 *
 * Copyright (C) 2012 Michael Ossmann <mike@ossmann.com>
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

#ifndef LPC43XX_RITIMER_H
#define LPC43XX_RITIMER_H

/**@{*/

#include <libopencm3/cm3/common.h>
#include <libopencm3/lpc43xx/memorymap.h>

#ifdef __cplusplus
extern "C" {
#endif

/* --- Repetitive Interrupt Timer registers -------------------------------- */

/* Compare register */
#define RITIMER_COMPVAL                 MMIO32(RITIMER_BASE + 0x000)

/* Mask register */
#define RITIMER_MASK                    MMIO32(RITIMER_BASE + 0x004)

/* Control register */
#define RITIMER_CTRL                    MMIO32(RITIMER_BASE + 0x008)

/* 32-bit counter */
#define RITIMER_COUNTER                 MMIO32(RITIMER_BASE + 0x00C)

/* --- RITIMER_COMPVAL values ----------------------------------- */

/* RICOMP: Compare register */
#define RITIMER_COMPVAL_RICOMP_SHIFT (0)
#define RITIMER_COMPVAL_RICOMP_MASK (0xffffffff << RITIMER_COMPVAL_RICOMP_SHIFT)
#define RITIMER_COMPVAL_RICOMP(x) ((x) << RITIMER_COMPVAL_RICOMP_SHIFT)

/* --- RITIMER_MASK values -------------------------------------- */

/* RIMASK: Mask register */
#define RITIMER_MASK_RIMASK_SHIFT (0)
#define RITIMER_MASK_RIMASK_MASK (0xffffffff << RITIMER_MASK_RIMASK_SHIFT)
#define RITIMER_MASK_RIMASK(x) ((x) << RITIMER_MASK_RIMASK_SHIFT)

/* --- RITIMER_CTRL values -------------------------------------- */

/* RITINT: Interrupt flag */
#define RITIMER_CTRL_RITINT_SHIFT (0)
#define RITIMER_CTRL_RITINT_MASK (0x1 << RITIMER_CTRL_RITINT_SHIFT)
#define RITIMER_CTRL_RITINT(x) ((x) << RITIMER_CTRL_RITINT_SHIFT)

/* RITENCLR: Timer enable clear */
#define RITIMER_CTRL_RITENCLR_SHIFT (1)
#define RITIMER_CTRL_RITENCLR_MASK (0x1 << RITIMER_CTRL_RITENCLR_SHIFT)
#define RITIMER_CTRL_RITENCLR(x) ((x) << RITIMER_CTRL_RITENCLR_SHIFT)

/* RITENBR: Timer enable for debug */
#define RITIMER_CTRL_RITENBR_SHIFT (2)
#define RITIMER_CTRL_RITENBR_MASK (0x1 << RITIMER_CTRL_RITENBR_SHIFT)
#define RITIMER_CTRL_RITENBR(x) ((x) << RITIMER_CTRL_RITENBR_SHIFT)

/* RITEN: Timer enable */
#define RITIMER_CTRL_RITEN_SHIFT (3)
#define RITIMER_CTRL_RITEN_MASK (0x1 << RITIMER_CTRL_RITEN_SHIFT)
#define RITIMER_CTRL_RITEN(x) ((x) << RITIMER_CTRL_RITEN_SHIFT)

/* --- RITIMER_COUNTER values ----------------------------------- */

/* RICOUNTER: 32-bit up counter */
#define RITIMER_COUNTER_RICOUNTER_SHIFT (0)
#define RITIMER_COUNTER_RICOUNTER_MASK (0xffffffff << RITIMER_COUNTER_RICOUNTER_SHIFT)
#define RITIMER_COUNTER_RICOUNTER(x) ((x) << RITIMER_COUNTER_RICOUNTER_SHIFT)

/**@}*/

#ifdef __cplusplus
}
#endif

#endif
