/** @defgroup spifi_defines SPI Flash Interface (SPIFI) Defines

@brief <b>Defined Constants and Types for the LPC43xx SPI Flash Interface (SPIFI)</b>

@ingroup LPC43xx_defines

@version 1.0.0

@author @htmlonly &copy; @endhtmlonly 2014 Jared Boone <jared@sharebrained.com>

@date 16 January 2014

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

#ifndef LPC43XX_SPIFI_H
#define LPC43XX_SPIFI_H

/**@{*/

#include <libopencm3/cm3/common.h>
#include <libopencm3/lpc43xx/memorymap.h>

#ifdef __cplusplus
extern "C" {
#endif

/* --- Convenience macros -------------------------------------------------- */

/* SPIFI port base addresses (for convenience) */
#define SPIFI                           (SPIFI_BASE)

/* --- SPIFI registers ----------------------------------------------------- */

/* Control Register */
#define SPIFI_CTRL                      MMIO32(SPIFI_BASE + 0x000)

/* Command Register */
#define SPIFI_CMD                       MMIO32(SPIFI_BASE + 0x004)

/* Address Register */
#define SPIFI_ADDR                      MMIO32(SPIFI_BASE + 0x008)

/* Intermediate Data Register */
#define SPIFI_IDATA                     MMIO32(SPIFI_BASE + 0x00C)

/* Cache Limit Register */
#define SPIFI_CLIMIT                    MMIO32(SPIFI_BASE + 0x010)

/* Data Register */
#define SPIFI_DATA                      MMIO32(SPIFI_BASE + 0x014)
#define SPIFI_DATA_BYTE                 MMIO8(SPIFI_BASE + 0x014)

/* Memory Command Register */
#define SPIFI_MCMD                      MMIO32(SPIFI_BASE + 0x018)

/* Status Register */
#define SPIFI_STAT                      MMIO32(SPIFI_BASE + 0x01C)

/* --- SPIFI_CTRL values ---------------------------------------- */

/* TIMEOUT: Memory mode idle timeout */
#define SPIFI_CTRL_TIMEOUT_SHIFT (0)
#define SPIFI_CTRL_TIMEOUT_MASK (0xffff << SPIFI_CTRL_TIMEOUT_SHIFT)
#define SPIFI_CTRL_TIMEOUT(x) ((x) << SPIFI_CTRL_TIMEOUT_SHIFT)

/* CSHIGH: Minimum CS# high time */
#define SPIFI_CTRL_CSHIGH_SHIFT (16)
#define SPIFI_CTRL_CSHIGH_MASK (0xf << SPIFI_CTRL_CSHIGH_SHIFT)
#define SPIFI_CTRL_CSHIGH(x) ((x) << SPIFI_CTRL_CSHIGH_SHIFT)

/* D_PRFTCH_DIS: Disable speculative prefetch */
#define SPIFI_CTRL_D_PRFTCH_DIS_SHIFT (21)
#define SPIFI_CTRL_D_PRFTCH_DIS_MASK (0x1 << SPIFI_CTRL_D_PRFTCH_DIS_SHIFT)
#define SPIFI_CTRL_D_PRFTCH_DIS(x) ((x) << SPIFI_CTRL_D_PRFTCH_DIS_SHIFT)

/* INTEN: Enable command end interrupt */
#define SPIFI_CTRL_INTEN_SHIFT (22)
#define SPIFI_CTRL_INTEN_MASK (0x1 << SPIFI_CTRL_INTEN_SHIFT)
#define SPIFI_CTRL_INTEN(x) ((x) << SPIFI_CTRL_INTEN_SHIFT)

/* MODE3: SPI mode 3 select */
#define SPIFI_CTRL_MODE3_SHIFT (23)
#define SPIFI_CTRL_MODE3_MASK (0x1 << SPIFI_CTRL_MODE3_SHIFT)
#define SPIFI_CTRL_MODE3(x) ((x) << SPIFI_CTRL_MODE3_SHIFT)

/* PRFTCH_DIS: Disable prefetching of cache lines */
#define SPIFI_CTRL_PRFTCH_DIS_SHIFT (27)
#define SPIFI_CTRL_PRFTCH_DIS_MASK (0x1 << SPIFI_CTRL_PRFTCH_DIS_SHIFT)
#define SPIFI_CTRL_PRFTCH_DIS(x) ((x) << SPIFI_CTRL_PRFTCH_DIS_SHIFT)

/* DUAL: Select dual protocol */
#define SPIFI_CTRL_DUAL_SHIFT (28)
#define SPIFI_CTRL_DUAL_MASK (0x1 << SPIFI_CTRL_DUAL_SHIFT)
#define SPIFI_CTRL_DUAL(x) ((x) << SPIFI_CTRL_DUAL_SHIFT)

/* RFCLK: Read data on falling edge */
#define SPIFI_CTRL_RFCLK_SHIFT (29)
#define SPIFI_CTRL_RFCLK_MASK (0x1 << SPIFI_CTRL_RFCLK_SHIFT)
#define SPIFI_CTRL_RFCLK(x) ((x) << SPIFI_CTRL_RFCLK_SHIFT)

/* FBCLK: Feedback clock select */
#define SPIFI_CTRL_FBCLK_SHIFT (30)
#define SPIFI_CTRL_FBCLK_MASK (0x1 << SPIFI_CTRL_FBCLK_SHIFT)
#define SPIFI_CTRL_FBCLK(x) ((x) << SPIFI_CTRL_FBCLK_SHIFT)

/* DMAEN: DMA request output enable */
#define SPIFI_CTRL_DMAEN_SHIFT (31)
#define SPIFI_CTRL_DMAEN_MASK (0x1 << SPIFI_CTRL_DMAEN_SHIFT)
#define SPIFI_CTRL_DMAEN(x) ((x) << SPIFI_CTRL_DMAEN_SHIFT)

/* --- SPIFI_CMD values ----------------------------------------- */

/* DATALEN: Data bytes in command */
#define SPIFI_CMD_DATALEN_SHIFT (0)
#define SPIFI_CMD_DATALEN_MASK (0x3fff << SPIFI_CMD_DATALEN_SHIFT)
#define SPIFI_CMD_DATALEN(x) ((x) << SPIFI_CMD_DATALEN_SHIFT)

/* POLL: Poll at end of command */
#define SPIFI_CMD_POLL_SHIFT (14)
#define SPIFI_CMD_POLL_MASK (0x1 << SPIFI_CMD_POLL_SHIFT)
#define SPIFI_CMD_POLL(x) ((x) << SPIFI_CMD_POLL_SHIFT)

/* DOUT: Data output to serial flash */
#define SPIFI_CMD_DOUT_SHIFT (15)
#define SPIFI_CMD_DOUT_MASK (0x1 << SPIFI_CMD_DOUT_SHIFT)
#define SPIFI_CMD_DOUT(x) ((x) << SPIFI_CMD_DOUT_SHIFT)

/* INTLEN: Intermediate bytes before data */
#define SPIFI_CMD_INTLEN_SHIFT (16)
#define SPIFI_CMD_INTLEN_MASK (0x7 << SPIFI_CMD_INTLEN_SHIFT)
#define SPIFI_CMD_INTLEN(x) ((x) << SPIFI_CMD_INTLEN_SHIFT)

/* FIELDFORM: Form of command fields */
#define SPIFI_CMD_FIELDFORM_SHIFT (19)
#define SPIFI_CMD_FIELDFORM_MASK (0x3 << SPIFI_CMD_FIELDFORM_SHIFT)
#define SPIFI_CMD_FIELDFORM(x) ((x) << SPIFI_CMD_FIELDFORM_SHIFT)

/* FRAMEFORM: Form of the opcode/address fields */
#define SPIFI_CMD_FRAMEFORM_SHIFT (21)
#define SPIFI_CMD_FRAMEFORM_MASK (0x7 << SPIFI_CMD_FRAMEFORM_SHIFT)
#define SPIFI_CMD_FRAMEFORM(x) ((x) << SPIFI_CMD_FRAMEFORM_SHIFT)

/* OPCODE: Command opcode */
#define SPIFI_CMD_OPCODE_SHIFT (24)
#define SPIFI_CMD_OPCODE_MASK (0xff << SPIFI_CMD_OPCODE_SHIFT)
#define SPIFI_CMD_OPCODE(x) ((x) << SPIFI_CMD_OPCODE_SHIFT)

/* --- SPIFI_ADDR values ---------------------------------------- */

/* ADDRESS: Address field value */
#define SPIFI_ADDR_ADDRESS_SHIFT (0)
#define SPIFI_ADDR_ADDRESS_MASK (0xffffffff << SPIFI_ADDR_ADDRESS_SHIFT)
#define SPIFI_ADDR_ADDRESS(x) ((x) << SPIFI_ADDR_ADDRESS_SHIFT)

/* --- SPIFI_IDATA values --------------------------------------- */

/* IDATA: Intermediate bytes value */
#define SPIFI_IDATA_IDATA_SHIFT (0)
#define SPIFI_IDATA_IDATA_MASK (0xffffffff << SPIFI_IDATA_IDATA_SHIFT)
#define SPIFI_IDATA_IDATA(x) ((x) << SPIFI_IDATA_IDATA_SHIFT)

/* --- SPIFI_CLIMIT values -------------------------------------- */

/* CLIMIT: Upper limit of cacheable memory */
#define SPIFI_CLIMIT_CLIMIT_SHIFT (0)
#define SPIFI_CLIMIT_CLIMIT_MASK (0xffffffff << SPIFI_CLIMIT_CLIMIT_SHIFT)
#define SPIFI_CLIMIT_CLIMIT(x) ((x) << SPIFI_CLIMIT_CLIMIT_SHIFT)

/* --- SPIFI_DATA values ---------------------------------------- */

/* DATA: Input or output data */
#define SPIFI_DATA_DATA_SHIFT (0)
#define SPIFI_DATA_DATA_MASK (0xffffffff << SPIFI_DATA_DATA_SHIFT)
#define SPIFI_DATA_DATA(x) ((x) << SPIFI_DATA_DATA_SHIFT)

/* --- SPIFI_MCMD values ---------------------------------------- */

/* POLL: Must be zero */
#define SPIFI_MCMD_POLL_SHIFT (14)
#define SPIFI_MCMD_POLL_MASK (0x1 << SPIFI_MCMD_POLL_SHIFT)
#define SPIFI_MCMD_POLL(x) ((x) << SPIFI_MCMD_POLL_SHIFT)

/* DOUT: Must be zero */
#define SPIFI_MCMD_DOUT_SHIFT (15)
#define SPIFI_MCMD_DOUT_MASK (0x1 << SPIFI_MCMD_DOUT_SHIFT)
#define SPIFI_MCMD_DOUT(x) ((x) << SPIFI_MCMD_DOUT_SHIFT)

/* INTLEN: Intermediate bytes before data */
#define SPIFI_MCMD_INTLEN_SHIFT (16)
#define SPIFI_MCMD_INTLEN_MASK (0x7 << SPIFI_MCMD_INTLEN_SHIFT)
#define SPIFI_MCMD_INTLEN(x) ((x) << SPIFI_MCMD_INTLEN_SHIFT)

/* FIELDFORM: Form of command fields */
#define SPIFI_MCMD_FIELDFORM_SHIFT (19)
#define SPIFI_MCMD_FIELDFORM_MASK (0x3 << SPIFI_MCMD_FIELDFORM_SHIFT)
#define SPIFI_MCMD_FIELDFORM(x) ((x) << SPIFI_MCMD_FIELDFORM_SHIFT)

/* FRAMEFORM: Form of the opcode/address fields */
#define SPIFI_MCMD_FRAMEFORM_SHIFT (21)
#define SPIFI_MCMD_FRAMEFORM_MASK (0x7 << SPIFI_MCMD_FRAMEFORM_SHIFT)
#define SPIFI_MCMD_FRAMEFORM(x) ((x) << SPIFI_MCMD_FRAMEFORM_SHIFT)

/* OPCODE: Command opcode */
#define SPIFI_MCMD_OPCODE_SHIFT (24)
#define SPIFI_MCMD_OPCODE_MASK (0xff << SPIFI_MCMD_OPCODE_SHIFT)
#define SPIFI_MCMD_OPCODE(x) ((x) << SPIFI_MCMD_OPCODE_SHIFT)

/* --- SPIFI_STAT values ---------------------------------------- */

/* MCINIT: Memory command initialized */
#define SPIFI_STAT_MCINIT_SHIFT (0)
#define SPIFI_STAT_MCINIT_MASK (0x1 << SPIFI_STAT_MCINIT_SHIFT)
#define SPIFI_STAT_MCINIT(x) ((x) << SPIFI_STAT_MCINIT_SHIFT)

/* CMD: Command active */
#define SPIFI_STAT_CMD_SHIFT (1)
#define SPIFI_STAT_CMD_MASK (0x1 << SPIFI_STAT_CMD_SHIFT)
#define SPIFI_STAT_CMD(x) ((x) << SPIFI_STAT_CMD_SHIFT)

/* RESET: Abort current command/memory mode */
#define SPIFI_STAT_RESET_SHIFT (4)
#define SPIFI_STAT_RESET_MASK (0x1 << SPIFI_STAT_RESET_SHIFT)
#define SPIFI_STAT_RESET(x) ((x) << SPIFI_STAT_RESET_SHIFT)

/* INTRQ: Interrupt request status */
#define SPIFI_STAT_INTRQ_SHIFT (5)
#define SPIFI_STAT_INTRQ_MASK (0x1 << SPIFI_STAT_INTRQ_SHIFT)
#define SPIFI_STAT_INTRQ(x) ((x) << SPIFI_STAT_INTRQ_SHIFT)

/* VERSION: Peripheral hardware version */
#define SPIFI_STAT_VERSION_SHIFT (24)
#define SPIFI_STAT_VERSION_MASK (0xff << SPIFI_STAT_VERSION_SHIFT)
#define SPIFI_STAT_VERSION(x) ((x) << SPIFI_STAT_VERSION_SHIFT)


BEGIN_DECLS

END_DECLS

/**@}*/

#ifdef __cplusplus
}
#endif

#endif
