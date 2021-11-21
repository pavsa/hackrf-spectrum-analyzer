/** @defgroup spi_defines Serial Peripheral Interface Defines

@brief <b>Defined Constants and Types for the LPC43xx Serial Peripheral Interface</b>

@ingroup LPC43xx_defines

@version 1.0.0

@author @htmlonly &copy; @endhtmlonly 2013 Jared Boone <jared@sharebrained.com>

@date 15 November 2013

LGPL License Terms @ref lgpl_license
 */
/*
* This file is part of the libopencm3 project.
*
* Copyright (C) 2013 Jared Boone <jared@sharebrained.com>
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

#ifndef LPC43XX_SPI_H
#define LPC43XX_SPI_H

/**@{*/

#include <libopencm3/cm3/common.h>
#include <libopencm3/lpc43xx/memorymap.h>

#ifdef __cplusplus
extern "C" {
#endif

/* --- Convenience macros -------------------------------------------------- */

/* SPI port base addresses (for convenience) */
#define SPI                             (SPI_PORT_BASE)

/* --- SPI registers ----------------------------------------------------- */

/* Control Register */
#define SPI_CR                          MMIO32(SPI + 0x000)

/* Status Register */
#define SPI_SR                          MMIO32(SPI + 0x004)

/* Data Register */
#define SPI_DR                          MMIO32(SPI + 0x008)

/* Clock Counter Register */
#define SPI_CCR                         MMIO32(SPI + 0x00C)

/* Test Control Register */
#define SPI_TCR                         MMIO32(SPI + 0x010)

/* Test Status Register */
#define SPI_TSR                         MMIO32(SPI + 0x014)

/* Interrupt Flag */
#define SPI_INT                         MMIO32(SPI + 0x01C)

/* --- SPI_CR values -------------------------------------------- */

/* BITENABLE: Bit length enable */
#define SPI_CR_BITENABLE_SHIFT (2)
#define SPI_CR_BITENABLE_MASK (0x1 << SPI_CR_BITENABLE_SHIFT)
#define SPI_CR_BITENABLE(x) ((x) << SPI_CR_BITENABLE_SHIFT)

/* CPHA: Clock phase control */
#define SPI_CR_CPHA_SHIFT (3)
#define SPI_CR_CPHA_MASK (0x1 << SPI_CR_CPHA_SHIFT)
#define SPI_CR_CPHA(x) ((x) << SPI_CR_CPHA_SHIFT)

/* CPOL: Clock polarity control */
#define SPI_CR_CPOL_SHIFT (4)
#define SPI_CR_CPOL_MASK (0x1 << SPI_CR_CPOL_SHIFT)
#define SPI_CR_CPOL(x) ((x) << SPI_CR_CPOL_SHIFT)

/* MSTR: Master mode select */
#define SPI_CR_MSTR_SHIFT (5)
#define SPI_CR_MSTR_MASK (0x1 << SPI_CR_MSTR_SHIFT)
#define SPI_CR_MSTR(x) ((x) << SPI_CR_MSTR_SHIFT)

/* LSBF: LSB first */
#define SPI_CR_LSBF_SHIFT (6)
#define SPI_CR_LSBF_MASK (0x1 << SPI_CR_LSBF_SHIFT)
#define SPI_CR_LSBF(x) ((x) << SPI_CR_LSBF_SHIFT)

/* SPIE: Serial peripheral interrupt enable */
#define SPI_CR_SPIE_SHIFT (7)
#define SPI_CR_SPIE_MASK (0x1 << SPI_CR_SPIE_SHIFT)
#define SPI_CR_SPIE(x) ((x) << SPI_CR_SPIE_SHIFT)

/* BITS: Bits per transfer */
#define SPI_CR_BITS_SHIFT (8)
#define SPI_CR_BITS_MASK (0xf << SPI_CR_BITS_SHIFT)
#define SPI_CR_BITS(x) ((x) << SPI_CR_BITS_SHIFT)

/* SPIF: Interrupt */
#define SPI_CR_SPIF_SHIFT (0)
#define SPI_CR_SPIF_MASK (0x1 << SPI_CR_SPIF_SHIFT)
#define SPI_CR_SPIF(x) ((x) << SPI_CR_SPIF_SHIFT)

/* --- SPI_SR values -------------------------------------------- */

/* ABRT: Slave abort */
#define SPI_SR_ABRT_SHIFT (3)
#define SPI_SR_ABRT_MASK (0x1 << SPI_SR_ABRT_SHIFT)
#define SPI_SR_ABRT(x) ((x) << SPI_SR_ABRT_SHIFT)

/* MODF: Mode fault */
#define SPI_SR_MODF_SHIFT (4)
#define SPI_SR_MODF_MASK (0x1 << SPI_SR_MODF_SHIFT)
#define SPI_SR_MODF(x) ((x) << SPI_SR_MODF_SHIFT)

/* ROVR: Read overrun */
#define SPI_SR_ROVR_SHIFT (5)
#define SPI_SR_ROVR_MASK (0x1 << SPI_SR_ROVR_SHIFT)
#define SPI_SR_ROVR(x) ((x) << SPI_SR_ROVR_SHIFT)

/* WCOL: Write collision */
#define SPI_SR_WCOL_SHIFT (6)
#define SPI_SR_WCOL_MASK (0x1 << SPI_SR_WCOL_SHIFT)
#define SPI_SR_WCOL(x) ((x) << SPI_SR_WCOL_SHIFT)

/* SPIF: Transfer complete */
#define SPI_SR_SPIF_SHIFT (7)
#define SPI_SR_SPIF_MASK (0x1 << SPI_SR_SPIF_SHIFT)
#define SPI_SR_SPIF(x) ((x) << SPI_SR_SPIF_SHIFT)

/* --- SPI_DR values -------------------------------------------- */

/* DATA: Bi-directional data port */
#define SPI_DR_DATA_SHIFT (0)
#define SPI_DR_DATA_MASK (0xffff << SPI_DR_DATA_SHIFT)
#define SPI_DR_DATA(x) ((x) << SPI_DR_DATA_SHIFT)

/* --- SPI_CCR values ------------------------------------------- */

/* COUNTER: Clock counter setting */
#define SPI_CCR_COUNTER_SHIFT (0)
#define SPI_CCR_COUNTER_MASK (0xff << SPI_CCR_COUNTER_SHIFT)
#define SPI_CCR_COUNTER(x) ((x) << SPI_CCR_COUNTER_SHIFT)

/* --- SPI_TCR values ------------------------------------------- */

/* TEST: Test mode */
#define SPI_TCR_TEST_SHIFT (1)
#define SPI_TCR_TEST_MASK (0x7f << SPI_TCR_TEST_SHIFT)
#define SPI_TCR_TEST(x) ((x) << SPI_TCR_TEST_SHIFT)

/* --- SPI_TSR values ------------------------------------------- */

/* ABRT: Slave abort */
#define SPI_TSR_ABRT_SHIFT (3)
#define SPI_TSR_ABRT_MASK (0x1 << SPI_TSR_ABRT_SHIFT)
#define SPI_TSR_ABRT(x) ((x) << SPI_TSR_ABRT_SHIFT)

/* MODF: Mode fault */
#define SPI_TSR_MODF_SHIFT (4)
#define SPI_TSR_MODF_MASK (0x1 << SPI_TSR_MODF_SHIFT)
#define SPI_TSR_MODF(x) ((x) << SPI_TSR_MODF_SHIFT)

/* ROVR: Read overrun */
#define SPI_TSR_ROVR_SHIFT (5)
#define SPI_TSR_ROVR_MASK (0x1 << SPI_TSR_ROVR_SHIFT)
#define SPI_TSR_ROVR(x) ((x) << SPI_TSR_ROVR_SHIFT)

/* WCOL: Write collision */
#define SPI_TSR_WCOL_SHIFT (6)
#define SPI_TSR_WCOL_MASK (0x1 << SPI_TSR_WCOL_SHIFT)
#define SPI_TSR_WCOL(x) ((x) << SPI_TSR_WCOL_SHIFT)

/* SPIF: Transfer complete */
#define SPI_TSR_SPIF_SHIFT (7)
#define SPI_TSR_SPIF_MASK (0x1 << SPI_TSR_SPIF_SHIFT)
#define SPI_TSR_SPIF(x) ((x) << SPI_TSR_SPIF_SHIFT)

BEGIN_DECLS

/*****/

END_DECLS

/**@}*/

#ifdef __cplusplus
}
#endif

#endif
