/** @defgroup i2s_defines I2S Defines

@brief <b>Defined Constants and Types for the LPC43xx I2S</b>

@ingroup LPC43xx_defines

@version 1.0.0

@author @htmlonly &copy; @endhtmlonly 2012 Michael Ossmann <mike@ossmann.com>

@date 10 March 2013

LGPL License Terms @ref lgpl_license
 */
/*
 * This file is part of the libopencm3 project.
 *
 * Copyright (C) 2012 Michael Ossmann <mike@ossmann.com>
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

#ifndef LPC43XX_I2S_H
#define LPC43XX_I2S_H

/**@{*/

#include <libopencm3/cm3/common.h>
#include <libopencm3/lpc43xx/memorymap.h>

#ifdef __cplusplus
extern "C" {
#endif

/* --- Convenience macros -------------------------------------------------- */

/* I2S port base addresses (for convenience) */
#define I2S0                            I2S0_BASE
#define I2S1                            I2S1_BASE

/* --- I2S registers ------------------------------------------------------- */

/* I2S Digital Audio Output Register */
#define I2S_DAO(port)                   MMIO32(port + 0x000)
#define I2S0_DAO                        I2S_DAO(I2S0)
#define I2S1_DAO                        I2S_DAO(I2S1)

/* I2S Digital Audio Input Register */
#define I2S_DAI(port)                   MMIO32(port + 0x004)
#define I2S0_DAI                        I2S_DAI(I2S0)
#define I2S1_DAI                        I2S_DAI(I2S1)

/* I2S Transmit FIFO */
#define I2S_TXFIFO(port)                MMIO32(port + 0x008)
#define I2S0_TXFIFO                     I2S_TXFIFO(I2S0)
#define I2S1_TXFIFO                     I2S_TXFIFO(I2S1)

/* I2S Receive FIFO */
#define I2S_RXFIFO(port)                MMIO32(port + 0x00C)
#define I2S0_RXFIFO                     I2S_RXFIFO(I2S0)
#define I2S1_RXFIFO                     I2S_RXFIFO(I2S1)

/* I2S Status Feedback Register */
#define I2S_STATE(port)                 MMIO32(port + 0x010)
#define I2S0_STATE                      I2S_STATE(I2S0)
#define I2S1_STATE                      I2S_STATE(I2S1)

/* I2S DMA Configuration Register 1 */
#define I2S_DMA1(port)                  MMIO32(port + 0x014)
#define I2S0_DMA1                       I2S_DMA1(I2S0)
#define I2S1_DMA1                       I2S_DMA1(I2S1)

/* I2S DMA Configuration Register 2 */
#define I2S_DMA2(port)                  MMIO32(port + 0x018)
#define I2S0_DMA2                       I2S_DMA2(I2S0)
#define I2S1_DMA2                       I2S_DMA2(I2S1)

/* I2S Interrupt Request Control Register */
#define I2S_IRQ(port)                   MMIO32(port + 0x01C)
#define I2S0_IRQ                        I2S_IRQ(I2S0)
#define I2S1_IRQ                        I2S_IRQ(I2S1)

/* I2S Transmit MCLK divider */
#define I2S_TXRATE(port)                MMIO32(port + 0x020)
#define I2S0_TXRATE                     I2S_TXRATE(I2S0)
#define I2S1_TXRATE                     I2S_TXRATE(I2S1)

/* I2S Receive MCLK divider */
#define I2S_RXRATE(port)                MMIO32(port + 0x024)
#define I2S0_RXRATE                     I2S_RXRATE(I2S0)
#define I2S1_RXRATE                     I2S_RXRATE(I2S1)

/* I2S Transmit bit rate divider */
#define I2S_TXBITRATE(port)             MMIO32(port + 0x028)
#define I2S0_TXBITRATE                  I2S_TXBITRATE(I2S0)
#define I2S1_TXBITRATE                  I2S_TXBITRATE(I2S1)

/* I2S Receive bit rate divider */
#define I2S_RXBITRATE(port)             MMIO32(port + 0x02C)
#define I2S0_RXBITRATE                  I2S_RXBITRATE(I2S0)
#define I2S1_RXBITRATE                  I2S_RXBITRATE(I2S1)

/* I2S Transmit mode control */
#define I2S_TXMODE(port)                MMIO32(port + 0x030)
#define I2S0_TXMODE                     I2S_TXMODE(I2S0)
#define I2S1_TXMODE                     I2S_TXMODE(I2S1)

/* I2S Receive mode control */
#define I2S_RXMODE(port)                MMIO32(port + 0x034)
#define I2S0_RXMODE                     I2S_RXMODE(I2S0)
#define I2S1_RXMODE                     I2S_RXMODE(I2S1)

/* --- I2S0_DAO values ------------------------------------------ */

/* WORDWIDTH: Selects the number of bytes in data */
#define I2S0_DAO_WORDWIDTH_SHIFT (0)
#define I2S0_DAO_WORDWIDTH_MASK (0x3 << I2S0_DAO_WORDWIDTH_SHIFT)
#define I2S0_DAO_WORDWIDTH(x) ((x) << I2S0_DAO_WORDWIDTH_SHIFT)

/* MONO: When 1, data is of monaural format. When 0, the data is in stereo format */
#define I2S0_DAO_MONO_SHIFT (2)
#define I2S0_DAO_MONO_MASK (0x1 << I2S0_DAO_MONO_SHIFT)
#define I2S0_DAO_MONO(x) ((x) << I2S0_DAO_MONO_SHIFT)

/* STOP: When 1, disables accesses on FIFOs, places the transmit channel in mute mode */
#define I2S0_DAO_STOP_SHIFT (3)
#define I2S0_DAO_STOP_MASK (0x1 << I2S0_DAO_STOP_SHIFT)
#define I2S0_DAO_STOP(x) ((x) << I2S0_DAO_STOP_SHIFT)

/* RESET: When 1, asynchronously resets the transmit channel and FIFO */
#define I2S0_DAO_RESET_SHIFT (4)
#define I2S0_DAO_RESET_MASK (0x1 << I2S0_DAO_RESET_SHIFT)
#define I2S0_DAO_RESET(x) ((x) << I2S0_DAO_RESET_SHIFT)

/* WS_SEL: When 0, the interface is in master mode. When 1, the interface is in slave mode */
#define I2S0_DAO_WS_SEL_SHIFT (5)
#define I2S0_DAO_WS_SEL_MASK (0x1 << I2S0_DAO_WS_SEL_SHIFT)
#define I2S0_DAO_WS_SEL(x) ((x) << I2S0_DAO_WS_SEL_SHIFT)

/* WS_HALFPERIOD: Word select half period minus 1, i.e. WS 64clk period -> ws_halfperiod = 31. */
#define I2S0_DAO_WS_HALFPERIOD_SHIFT (6)
#define I2S0_DAO_WS_HALFPERIOD_MASK (0x1ff << I2S0_DAO_WS_HALFPERIOD_SHIFT)
#define I2S0_DAO_WS_HALFPERIOD(x) ((x) << I2S0_DAO_WS_HALFPERIOD_SHIFT)

/* MUTE: When 1, the transmit channel sends only zeroes */
#define I2S0_DAO_MUTE_SHIFT (15)
#define I2S0_DAO_MUTE_MASK (0x1 << I2S0_DAO_MUTE_SHIFT)
#define I2S0_DAO_MUTE(x) ((x) << I2S0_DAO_MUTE_SHIFT)

/* --- I2S1_DAO values ------------------------------------------ */

/* WORDWIDTH: Selects the number of bytes in data */
#define I2S1_DAO_WORDWIDTH_SHIFT (0)
#define I2S1_DAO_WORDWIDTH_MASK (0x3 << I2S1_DAO_WORDWIDTH_SHIFT)
#define I2S1_DAO_WORDWIDTH(x) ((x) << I2S1_DAO_WORDWIDTH_SHIFT)

/* MONO: When 1, data is of monaural format. When 0, the data is in stereo format */
#define I2S1_DAO_MONO_SHIFT (2)
#define I2S1_DAO_MONO_MASK (0x1 << I2S1_DAO_MONO_SHIFT)
#define I2S1_DAO_MONO(x) ((x) << I2S1_DAO_MONO_SHIFT)

/* STOP: When 1, disables accesses on FIFOs, places the transmit channel in mute mode */
#define I2S1_DAO_STOP_SHIFT (3)
#define I2S1_DAO_STOP_MASK (0x1 << I2S1_DAO_STOP_SHIFT)
#define I2S1_DAO_STOP(x) ((x) << I2S1_DAO_STOP_SHIFT)

/* RESET: When 1, asynchronously resets the transmit channel and FIFO */
#define I2S1_DAO_RESET_SHIFT (4)
#define I2S1_DAO_RESET_MASK (0x1 << I2S1_DAO_RESET_SHIFT)
#define I2S1_DAO_RESET(x) ((x) << I2S1_DAO_RESET_SHIFT)

/* WS_SEL: When 0, the interface is in master mode. When 1, the interface is in slave mode */
#define I2S1_DAO_WS_SEL_SHIFT (5)
#define I2S1_DAO_WS_SEL_MASK (0x1 << I2S1_DAO_WS_SEL_SHIFT)
#define I2S1_DAO_WS_SEL(x) ((x) << I2S1_DAO_WS_SEL_SHIFT)

/* WS_HALFPERIOD: Word select half period minus 1, i.e. WS 64clk period -> ws_halfperiod = 31. */
#define I2S1_DAO_WS_HALFPERIOD_SHIFT (6)
#define I2S1_DAO_WS_HALFPERIOD_MASK (0x1ff << I2S1_DAO_WS_HALFPERIOD_SHIFT)
#define I2S1_DAO_WS_HALFPERIOD(x) ((x) << I2S1_DAO_WS_HALFPERIOD_SHIFT)

/* MUTE: When 1, the transmit channel sends only zeroes */
#define I2S1_DAO_MUTE_SHIFT (15)
#define I2S1_DAO_MUTE_MASK (0x1 << I2S1_DAO_MUTE_SHIFT)
#define I2S1_DAO_MUTE(x) ((x) << I2S1_DAO_MUTE_SHIFT)

/* --- I2S0_DAI values ------------------------------------------ */

/* WORDWIDTH: Selects the number of bytes in data */
#define I2S0_DAI_WORDWIDTH_SHIFT (0)
#define I2S0_DAI_WORDWIDTH_MASK (0x3 << I2S0_DAI_WORDWIDTH_SHIFT)
#define I2S0_DAI_WORDWIDTH(x) ((x) << I2S0_DAI_WORDWIDTH_SHIFT)

/* MONO: When 1, data is of monaural format. When 0, the data is in stereo format */
#define I2S0_DAI_MONO_SHIFT (2)
#define I2S0_DAI_MONO_MASK (0x1 << I2S0_DAI_MONO_SHIFT)
#define I2S0_DAI_MONO(x) ((x) << I2S0_DAI_MONO_SHIFT)

/* STOP: When 1, disables accesses on FIFOs, places the transmit channel in mute mode */
#define I2S0_DAI_STOP_SHIFT (3)
#define I2S0_DAI_STOP_MASK (0x1 << I2S0_DAI_STOP_SHIFT)
#define I2S0_DAI_STOP(x) ((x) << I2S0_DAI_STOP_SHIFT)

/* RESET: When 1, asynchronously resets the transmit channel and FIFO */
#define I2S0_DAI_RESET_SHIFT (4)
#define I2S0_DAI_RESET_MASK (0x1 << I2S0_DAI_RESET_SHIFT)
#define I2S0_DAI_RESET(x) ((x) << I2S0_DAI_RESET_SHIFT)

/* WS_SEL: When 0, the interface is in master mode. When 1, the interface is in slave mode */
#define I2S0_DAI_WS_SEL_SHIFT (5)
#define I2S0_DAI_WS_SEL_MASK (0x1 << I2S0_DAI_WS_SEL_SHIFT)
#define I2S0_DAI_WS_SEL(x) ((x) << I2S0_DAI_WS_SEL_SHIFT)

/* WS_HALFPERIOD: Word select half period minus 1, i.e. WS 64clk period -> ws_halfperiod = 31. */
#define I2S0_DAI_WS_HALFPERIOD_SHIFT (6)
#define I2S0_DAI_WS_HALFPERIOD_MASK (0x1ff << I2S0_DAI_WS_HALFPERIOD_SHIFT)
#define I2S0_DAI_WS_HALFPERIOD(x) ((x) << I2S0_DAI_WS_HALFPERIOD_SHIFT)

/* --- I2S1_DAI values ------------------------------------------ */

/* WORDWIDTH: Selects the number of bytes in data */
#define I2S1_DAI_WORDWIDTH_SHIFT (0)
#define I2S1_DAI_WORDWIDTH_MASK (0x3 << I2S1_DAI_WORDWIDTH_SHIFT)
#define I2S1_DAI_WORDWIDTH(x) ((x) << I2S1_DAI_WORDWIDTH_SHIFT)

/* MONO: When 1, data is of monaural format. When 0, the data is in stereo format */
#define I2S1_DAI_MONO_SHIFT (2)
#define I2S1_DAI_MONO_MASK (0x1 << I2S1_DAI_MONO_SHIFT)
#define I2S1_DAI_MONO(x) ((x) << I2S1_DAI_MONO_SHIFT)

/* STOP: When 1, disables accesses on FIFOs, places the transmit channel in mute mode */
#define I2S1_DAI_STOP_SHIFT (3)
#define I2S1_DAI_STOP_MASK (0x1 << I2S1_DAI_STOP_SHIFT)
#define I2S1_DAI_STOP(x) ((x) << I2S1_DAI_STOP_SHIFT)

/* RESET: When 1, asynchronously resets the transmit channel and FIFO */
#define I2S1_DAI_RESET_SHIFT (4)
#define I2S1_DAI_RESET_MASK (0x1 << I2S1_DAI_RESET_SHIFT)
#define I2S1_DAI_RESET(x) ((x) << I2S1_DAI_RESET_SHIFT)

/* WS_SEL: When 0, the interface is in master mode. When 1, the interface is in slave mode */
#define I2S1_DAI_WS_SEL_SHIFT (5)
#define I2S1_DAI_WS_SEL_MASK (0x1 << I2S1_DAI_WS_SEL_SHIFT)
#define I2S1_DAI_WS_SEL(x) ((x) << I2S1_DAI_WS_SEL_SHIFT)

/* WS_HALFPERIOD: Word select half period minus 1, i.e. WS 64clk period -> ws_halfperiod = 31. */
#define I2S1_DAI_WS_HALFPERIOD_SHIFT (6)
#define I2S1_DAI_WS_HALFPERIOD_MASK (0x1ff << I2S1_DAI_WS_HALFPERIOD_SHIFT)
#define I2S1_DAI_WS_HALFPERIOD(x) ((x) << I2S1_DAI_WS_HALFPERIOD_SHIFT)

/* --- I2S0_TXFIFO values --------------------------------------- */

/* I2STXFIFO: 8 x 32-bit transmit FIFO */
#define I2S0_TXFIFO_I2STXFIFO_SHIFT (0)
#define I2S0_TXFIFO_I2STXFIFO_MASK (0xffffffff << I2S0_TXFIFO_I2STXFIFO_SHIFT)
#define I2S0_TXFIFO_I2STXFIFO(x) ((x) << I2S0_TXFIFO_I2STXFIFO_SHIFT)

/* --- I2S1_TXFIFO values --------------------------------------- */

/* I2STXFIFO: 8 x 32-bit transmit FIFO */
#define I2S1_TXFIFO_I2STXFIFO_SHIFT (0)
#define I2S1_TXFIFO_I2STXFIFO_MASK (0xffffffff << I2S1_TXFIFO_I2STXFIFO_SHIFT)
#define I2S1_TXFIFO_I2STXFIFO(x) ((x) << I2S1_TXFIFO_I2STXFIFO_SHIFT)

/* --- I2S0_RXFIFO values --------------------------------------- */

/* I2SRXFIFO: 8 x 32-bit receive FIFO */
#define I2S0_RXFIFO_I2SRXFIFO_SHIFT (0)
#define I2S0_RXFIFO_I2SRXFIFO_MASK (0xffffffff << I2S0_RXFIFO_I2SRXFIFO_SHIFT)
#define I2S0_RXFIFO_I2SRXFIFO(x) ((x) << I2S0_RXFIFO_I2SRXFIFO_SHIFT)

/* --- I2S1_RXFIFO values --------------------------------------- */

/* I2SRXFIFO: 8 x 32-bit receive FIFO */
#define I2S1_RXFIFO_I2SRXFIFO_SHIFT (0)
#define I2S1_RXFIFO_I2SRXFIFO_MASK (0xffffffff << I2S1_RXFIFO_I2SRXFIFO_SHIFT)
#define I2S1_RXFIFO_I2SRXFIFO(x) ((x) << I2S1_RXFIFO_I2SRXFIFO_SHIFT)

/* --- I2S0_STATE values ---------------------------------------- */

/* IRQ: This bit reflects the presence of Receive Interrupt or Transmit Interrupt */
#define I2S0_STATE_IRQ_SHIFT (0)
#define I2S0_STATE_IRQ_MASK (0x1 << I2S0_STATE_IRQ_SHIFT)
#define I2S0_STATE_IRQ(x) ((x) << I2S0_STATE_IRQ_SHIFT)

/* DMAREQ1: This bit reflects the presence of Receive or Transmit DMA Request 1 */
#define I2S0_STATE_DMAREQ1_SHIFT (1)
#define I2S0_STATE_DMAREQ1_MASK (0x1 << I2S0_STATE_DMAREQ1_SHIFT)
#define I2S0_STATE_DMAREQ1(x) ((x) << I2S0_STATE_DMAREQ1_SHIFT)

/* DMAREQ2: This bit reflects the presence of Receive or Transmit DMA Request 2 */
#define I2S0_STATE_DMAREQ2_SHIFT (2)
#define I2S0_STATE_DMAREQ2_MASK (0x1 << I2S0_STATE_DMAREQ2_SHIFT)
#define I2S0_STATE_DMAREQ2(x) ((x) << I2S0_STATE_DMAREQ2_SHIFT)

/* RX_LEVEL: Reflects the current level of the Receive FIFO */
#define I2S0_STATE_RX_LEVEL_SHIFT (8)
#define I2S0_STATE_RX_LEVEL_MASK (0xf << I2S0_STATE_RX_LEVEL_SHIFT)
#define I2S0_STATE_RX_LEVEL(x) ((x) << I2S0_STATE_RX_LEVEL_SHIFT)

/* TX_LEVEL: Reflects the current level of the Transmit FIFO */
#define I2S0_STATE_TX_LEVEL_SHIFT (16)
#define I2S0_STATE_TX_LEVEL_MASK (0xf << I2S0_STATE_TX_LEVEL_SHIFT)
#define I2S0_STATE_TX_LEVEL(x) ((x) << I2S0_STATE_TX_LEVEL_SHIFT)

/* --- I2S1_STATE values ---------------------------------------- */

/* IRQ: This bit reflects the presence of Receive Interrupt or Transmit Interrupt */
#define I2S1_STATE_IRQ_SHIFT (0)
#define I2S1_STATE_IRQ_MASK (0x1 << I2S1_STATE_IRQ_SHIFT)
#define I2S1_STATE_IRQ(x) ((x) << I2S1_STATE_IRQ_SHIFT)

/* DMAREQ1: This bit reflects the presence of Receive or Transmit DMA Request 1 */
#define I2S1_STATE_DMAREQ1_SHIFT (1)
#define I2S1_STATE_DMAREQ1_MASK (0x1 << I2S1_STATE_DMAREQ1_SHIFT)
#define I2S1_STATE_DMAREQ1(x) ((x) << I2S1_STATE_DMAREQ1_SHIFT)

/* DMAREQ2: This bit reflects the presence of Receive or Transmit DMA Request 2 */
#define I2S1_STATE_DMAREQ2_SHIFT (2)
#define I2S1_STATE_DMAREQ2_MASK (0x1 << I2S1_STATE_DMAREQ2_SHIFT)
#define I2S1_STATE_DMAREQ2(x) ((x) << I2S1_STATE_DMAREQ2_SHIFT)

/* RX_LEVEL: Reflects the current level of the Receive FIFO */
#define I2S1_STATE_RX_LEVEL_SHIFT (8)
#define I2S1_STATE_RX_LEVEL_MASK (0xf << I2S1_STATE_RX_LEVEL_SHIFT)
#define I2S1_STATE_RX_LEVEL(x) ((x) << I2S1_STATE_RX_LEVEL_SHIFT)

/* TX_LEVEL: Reflects the current level of the Transmit FIFO */
#define I2S1_STATE_TX_LEVEL_SHIFT (16)
#define I2S1_STATE_TX_LEVEL_MASK (0xf << I2S1_STATE_TX_LEVEL_SHIFT)
#define I2S1_STATE_TX_LEVEL(x) ((x) << I2S1_STATE_TX_LEVEL_SHIFT)

/* --- I2S0_DMA1 values ----------------------------------------- */

/* RX_DMA1_ENABLE: When 1, enables DMA1 for I2S receive */
#define I2S0_DMA1_RX_DMA1_ENABLE_SHIFT (0)
#define I2S0_DMA1_RX_DMA1_ENABLE_MASK (0x1 << I2S0_DMA1_RX_DMA1_ENABLE_SHIFT)
#define I2S0_DMA1_RX_DMA1_ENABLE(x) ((x) << I2S0_DMA1_RX_DMA1_ENABLE_SHIFT)

/* TX_DMA1_ENABLE: When 1, enables DMA1 for I2S transmit */
#define I2S0_DMA1_TX_DMA1_ENABLE_SHIFT (1)
#define I2S0_DMA1_TX_DMA1_ENABLE_MASK (0x1 << I2S0_DMA1_TX_DMA1_ENABLE_SHIFT)
#define I2S0_DMA1_TX_DMA1_ENABLE(x) ((x) << I2S0_DMA1_TX_DMA1_ENABLE_SHIFT)

/* RX_DEPTH_DMA1: Set the FIFO level that triggers a receive DMA request on DMA1 */
#define I2S0_DMA1_RX_DEPTH_DMA1_SHIFT (8)
#define I2S0_DMA1_RX_DEPTH_DMA1_MASK (0xf << I2S0_DMA1_RX_DEPTH_DMA1_SHIFT)
#define I2S0_DMA1_RX_DEPTH_DMA1(x) ((x) << I2S0_DMA1_RX_DEPTH_DMA1_SHIFT)

/* TX_DEPTH_DMA1: Set the FIFO level that triggers a transmit DMA request on DMA1 */
#define I2S0_DMA1_TX_DEPTH_DMA1_SHIFT (16)
#define I2S0_DMA1_TX_DEPTH_DMA1_MASK (0xf << I2S0_DMA1_TX_DEPTH_DMA1_SHIFT)
#define I2S0_DMA1_TX_DEPTH_DMA1(x) ((x) << I2S0_DMA1_TX_DEPTH_DMA1_SHIFT)

/* --- I2S1_DMA1 values ----------------------------------------- */

/* RX_DMA1_ENABLE: When 1, enables DMA1 for I2S receive */
#define I2S1_DMA1_RX_DMA1_ENABLE_SHIFT (0)
#define I2S1_DMA1_RX_DMA1_ENABLE_MASK (0x1 << I2S1_DMA1_RX_DMA1_ENABLE_SHIFT)
#define I2S1_DMA1_RX_DMA1_ENABLE(x) ((x) << I2S1_DMA1_RX_DMA1_ENABLE_SHIFT)

/* TX_DMA1_ENABLE: When 1, enables DMA1 for I2S transmit */
#define I2S1_DMA1_TX_DMA1_ENABLE_SHIFT (1)
#define I2S1_DMA1_TX_DMA1_ENABLE_MASK (0x1 << I2S1_DMA1_TX_DMA1_ENABLE_SHIFT)
#define I2S1_DMA1_TX_DMA1_ENABLE(x) ((x) << I2S1_DMA1_TX_DMA1_ENABLE_SHIFT)

/* RX_DEPTH_DMA1: Set the FIFO level that triggers a receive DMA request on DMA1 */
#define I2S1_DMA1_RX_DEPTH_DMA1_SHIFT (8)
#define I2S1_DMA1_RX_DEPTH_DMA1_MASK (0xf << I2S1_DMA1_RX_DEPTH_DMA1_SHIFT)
#define I2S1_DMA1_RX_DEPTH_DMA1(x) ((x) << I2S1_DMA1_RX_DEPTH_DMA1_SHIFT)

/* TX_DEPTH_DMA1: Set the FIFO level that triggers a transmit DMA request on DMA1 */
#define I2S1_DMA1_TX_DEPTH_DMA1_SHIFT (16)
#define I2S1_DMA1_TX_DEPTH_DMA1_MASK (0xf << I2S1_DMA1_TX_DEPTH_DMA1_SHIFT)
#define I2S1_DMA1_TX_DEPTH_DMA1(x) ((x) << I2S1_DMA1_TX_DEPTH_DMA1_SHIFT)

/* --- I2S0_DMA2 values ----------------------------------------- */

/* RX_DMA2_ENABLE: When 1, enables DMA2 for I2S receive */
#define I2S0_DMA2_RX_DMA2_ENABLE_SHIFT (0)
#define I2S0_DMA2_RX_DMA2_ENABLE_MASK (0x1 << I2S0_DMA2_RX_DMA2_ENABLE_SHIFT)
#define I2S0_DMA2_RX_DMA2_ENABLE(x) ((x) << I2S0_DMA2_RX_DMA2_ENABLE_SHIFT)

/* TX_DMA2_ENABLE: When 1, enables DMA2 for I2S transmit */
#define I2S0_DMA2_TX_DMA2_ENABLE_SHIFT (1)
#define I2S0_DMA2_TX_DMA2_ENABLE_MASK (0x1 << I2S0_DMA2_TX_DMA2_ENABLE_SHIFT)
#define I2S0_DMA2_TX_DMA2_ENABLE(x) ((x) << I2S0_DMA2_TX_DMA2_ENABLE_SHIFT)

/* RX_DEPTH_DMA2: Set the FIFO level that triggers a receive DMA request on DMA2 */
#define I2S0_DMA2_RX_DEPTH_DMA2_SHIFT (8)
#define I2S0_DMA2_RX_DEPTH_DMA2_MASK (0xf << I2S0_DMA2_RX_DEPTH_DMA2_SHIFT)
#define I2S0_DMA2_RX_DEPTH_DMA2(x) ((x) << I2S0_DMA2_RX_DEPTH_DMA2_SHIFT)

/* TX_DEPTH_DMA2: Set the FIFO level that triggers a transmit DMA request on DMA2 */
#define I2S0_DMA2_TX_DEPTH_DMA2_SHIFT (16)
#define I2S0_DMA2_TX_DEPTH_DMA2_MASK (0xf << I2S0_DMA2_TX_DEPTH_DMA2_SHIFT)
#define I2S0_DMA2_TX_DEPTH_DMA2(x) ((x) << I2S0_DMA2_TX_DEPTH_DMA2_SHIFT)

/* --- I2S1_DMA2 values ----------------------------------------- */

/* RX_DMA2_ENABLE: When 1, enables DMA2 for I2S receive */
#define I2S1_DMA2_RX_DMA2_ENABLE_SHIFT (0)
#define I2S1_DMA2_RX_DMA2_ENABLE_MASK (0x1 << I2S1_DMA2_RX_DMA2_ENABLE_SHIFT)
#define I2S1_DMA2_RX_DMA2_ENABLE(x) ((x) << I2S1_DMA2_RX_DMA2_ENABLE_SHIFT)

/* TX_DMA2_ENABLE: When 1, enables DMA2 for I2S transmit */
#define I2S1_DMA2_TX_DMA2_ENABLE_SHIFT (1)
#define I2S1_DMA2_TX_DMA2_ENABLE_MASK (0x1 << I2S1_DMA2_TX_DMA2_ENABLE_SHIFT)
#define I2S1_DMA2_TX_DMA2_ENABLE(x) ((x) << I2S1_DMA2_TX_DMA2_ENABLE_SHIFT)

/* RX_DEPTH_DMA2: Set the FIFO level that triggers a receive DMA request on DMA2 */
#define I2S1_DMA2_RX_DEPTH_DMA2_SHIFT (8)
#define I2S1_DMA2_RX_DEPTH_DMA2_MASK (0xf << I2S1_DMA2_RX_DEPTH_DMA2_SHIFT)
#define I2S1_DMA2_RX_DEPTH_DMA2(x) ((x) << I2S1_DMA2_RX_DEPTH_DMA2_SHIFT)

/* TX_DEPTH_DMA2: Set the FIFO level that triggers a transmit DMA request on DMA2 */
#define I2S1_DMA2_TX_DEPTH_DMA2_SHIFT (16)
#define I2S1_DMA2_TX_DEPTH_DMA2_MASK (0xf << I2S1_DMA2_TX_DEPTH_DMA2_SHIFT)
#define I2S1_DMA2_TX_DEPTH_DMA2(x) ((x) << I2S1_DMA2_TX_DEPTH_DMA2_SHIFT)

/* --- I2S0_IRQ values ------------------------------------------ */

/* RX_IRQ_ENABLE: When 1, enables I2S receive interrupt */
#define I2S0_IRQ_RX_IRQ_ENABLE_SHIFT (0)
#define I2S0_IRQ_RX_IRQ_ENABLE_MASK (0x1 << I2S0_IRQ_RX_IRQ_ENABLE_SHIFT)
#define I2S0_IRQ_RX_IRQ_ENABLE(x) ((x) << I2S0_IRQ_RX_IRQ_ENABLE_SHIFT)

/* TX_IRQ_ENABLE: When 1, enables I2S transmit interrupt */
#define I2S0_IRQ_TX_IRQ_ENABLE_SHIFT (1)
#define I2S0_IRQ_TX_IRQ_ENABLE_MASK (0x1 << I2S0_IRQ_TX_IRQ_ENABLE_SHIFT)
#define I2S0_IRQ_TX_IRQ_ENABLE(x) ((x) << I2S0_IRQ_TX_IRQ_ENABLE_SHIFT)

/* RX_DEPTH_IRQ: Set the FIFO level on which to create an irq request. */
#define I2S0_IRQ_RX_DEPTH_IRQ_SHIFT (8)
#define I2S0_IRQ_RX_DEPTH_IRQ_MASK (0xf << I2S0_IRQ_RX_DEPTH_IRQ_SHIFT)
#define I2S0_IRQ_RX_DEPTH_IRQ(x) ((x) << I2S0_IRQ_RX_DEPTH_IRQ_SHIFT)

/* TX_DEPTH_IRQ: Set the FIFO level on which to create an irq request. */
#define I2S0_IRQ_TX_DEPTH_IRQ_SHIFT (16)
#define I2S0_IRQ_TX_DEPTH_IRQ_MASK (0xf << I2S0_IRQ_TX_DEPTH_IRQ_SHIFT)
#define I2S0_IRQ_TX_DEPTH_IRQ(x) ((x) << I2S0_IRQ_TX_DEPTH_IRQ_SHIFT)

/* --- I2S1_IRQ values ------------------------------------------ */

/* RX_IRQ_ENABLE: When 1, enables I2S receive interrupt */
#define I2S1_IRQ_RX_IRQ_ENABLE_SHIFT (0)
#define I2S1_IRQ_RX_IRQ_ENABLE_MASK (0x1 << I2S1_IRQ_RX_IRQ_ENABLE_SHIFT)
#define I2S1_IRQ_RX_IRQ_ENABLE(x) ((x) << I2S1_IRQ_RX_IRQ_ENABLE_SHIFT)

/* TX_IRQ_ENABLE: When 1, enables I2S transmit interrupt */
#define I2S1_IRQ_TX_IRQ_ENABLE_SHIFT (1)
#define I2S1_IRQ_TX_IRQ_ENABLE_MASK (0x1 << I2S1_IRQ_TX_IRQ_ENABLE_SHIFT)
#define I2S1_IRQ_TX_IRQ_ENABLE(x) ((x) << I2S1_IRQ_TX_IRQ_ENABLE_SHIFT)

/* RX_DEPTH_IRQ: Set the FIFO level on which to create an irq request. */
#define I2S1_IRQ_RX_DEPTH_IRQ_SHIFT (8)
#define I2S1_IRQ_RX_DEPTH_IRQ_MASK (0xf << I2S1_IRQ_RX_DEPTH_IRQ_SHIFT)
#define I2S1_IRQ_RX_DEPTH_IRQ(x) ((x) << I2S1_IRQ_RX_DEPTH_IRQ_SHIFT)

/* TX_DEPTH_IRQ: Set the FIFO level on which to create an irq request. */
#define I2S1_IRQ_TX_DEPTH_IRQ_SHIFT (16)
#define I2S1_IRQ_TX_DEPTH_IRQ_MASK (0xf << I2S1_IRQ_TX_DEPTH_IRQ_SHIFT)
#define I2S1_IRQ_TX_DEPTH_IRQ(x) ((x) << I2S1_IRQ_TX_DEPTH_IRQ_SHIFT)

/* --- I2S0_TXRATE values --------------------------------------- */

/* Y_DIVIDER: I2S transmit MCLK rate denominator */
#define I2S0_TXRATE_Y_DIVIDER_SHIFT (0)
#define I2S0_TXRATE_Y_DIVIDER_MASK (0xff << I2S0_TXRATE_Y_DIVIDER_SHIFT)
#define I2S0_TXRATE_Y_DIVIDER(x) ((x) << I2S0_TXRATE_Y_DIVIDER_SHIFT)

/* X_DIVIDER: I2S transmit MCLK rate numerator */
#define I2S0_TXRATE_X_DIVIDER_SHIFT (8)
#define I2S0_TXRATE_X_DIVIDER_MASK (0xff << I2S0_TXRATE_X_DIVIDER_SHIFT)
#define I2S0_TXRATE_X_DIVIDER(x) ((x) << I2S0_TXRATE_X_DIVIDER_SHIFT)

/* --- I2S1_TXRATE values --------------------------------------- */

/* Y_DIVIDER: I2S transmit MCLK rate denominator */
#define I2S1_TXRATE_Y_DIVIDER_SHIFT (0)
#define I2S1_TXRATE_Y_DIVIDER_MASK (0xff << I2S1_TXRATE_Y_DIVIDER_SHIFT)
#define I2S1_TXRATE_Y_DIVIDER(x) ((x) << I2S1_TXRATE_Y_DIVIDER_SHIFT)

/* X_DIVIDER: I2S transmit MCLK rate numerator */
#define I2S1_TXRATE_X_DIVIDER_SHIFT (8)
#define I2S1_TXRATE_X_DIVIDER_MASK (0xff << I2S1_TXRATE_X_DIVIDER_SHIFT)
#define I2S1_TXRATE_X_DIVIDER(x) ((x) << I2S1_TXRATE_X_DIVIDER_SHIFT)

/* --- I2S0_RXRATE values --------------------------------------- */

/* Y_DIVIDER: I2S receive MCLK rate denominator */
#define I2S0_RXRATE_Y_DIVIDER_SHIFT (0)
#define I2S0_RXRATE_Y_DIVIDER_MASK (0xff << I2S0_RXRATE_Y_DIVIDER_SHIFT)
#define I2S0_RXRATE_Y_DIVIDER(x) ((x) << I2S0_RXRATE_Y_DIVIDER_SHIFT)

/* X_DIVIDER: I2S receive MCLK rate numerator */
#define I2S0_RXRATE_X_DIVIDER_SHIFT (8)
#define I2S0_RXRATE_X_DIVIDER_MASK (0xff << I2S0_RXRATE_X_DIVIDER_SHIFT)
#define I2S0_RXRATE_X_DIVIDER(x) ((x) << I2S0_RXRATE_X_DIVIDER_SHIFT)

/* --- I2S1_RXRATE values --------------------------------------- */

/* Y_DIVIDER: I2S receive MCLK rate denominator */
#define I2S1_RXRATE_Y_DIVIDER_SHIFT (0)
#define I2S1_RXRATE_Y_DIVIDER_MASK (0xff << I2S1_RXRATE_Y_DIVIDER_SHIFT)
#define I2S1_RXRATE_Y_DIVIDER(x) ((x) << I2S1_RXRATE_Y_DIVIDER_SHIFT)

/* X_DIVIDER: I2S receive MCLK rate numerator */
#define I2S1_RXRATE_X_DIVIDER_SHIFT (8)
#define I2S1_RXRATE_X_DIVIDER_MASK (0xff << I2S1_RXRATE_X_DIVIDER_SHIFT)
#define I2S1_RXRATE_X_DIVIDER(x) ((x) << I2S1_RXRATE_X_DIVIDER_SHIFT)

/* --- I2S0_TXBITRATE values ------------------------------------ */

/* TX_BITRATE: I2S transmit bit rate */
#define I2S0_TXBITRATE_TX_BITRATE_SHIFT (0)
#define I2S0_TXBITRATE_TX_BITRATE_MASK (0x3f << I2S0_TXBITRATE_TX_BITRATE_SHIFT)
#define I2S0_TXBITRATE_TX_BITRATE(x) ((x) << I2S0_TXBITRATE_TX_BITRATE_SHIFT)

/* --- I2S1_TXBITRATE values ------------------------------------ */

/* TX_BITRATE: I2S transmit bit rate */
#define I2S1_TXBITRATE_TX_BITRATE_SHIFT (0)
#define I2S1_TXBITRATE_TX_BITRATE_MASK (0x3f << I2S1_TXBITRATE_TX_BITRATE_SHIFT)
#define I2S1_TXBITRATE_TX_BITRATE(x) ((x) << I2S1_TXBITRATE_TX_BITRATE_SHIFT)

/* --- I2S0_RXBITRATE values ------------------------------------ */

/* RX_BITRATE: I2S receive bit rate */
#define I2S0_RXBITRATE_RX_BITRATE_SHIFT (0)
#define I2S0_RXBITRATE_RX_BITRATE_MASK (0x3f << I2S0_RXBITRATE_RX_BITRATE_SHIFT)
#define I2S0_RXBITRATE_RX_BITRATE(x) ((x) << I2S0_RXBITRATE_RX_BITRATE_SHIFT)

/* --- I2S1_RXBITRATE values ------------------------------------ */

/* RX_BITRATE: I2S receive bit rate */
#define I2S1_RXBITRATE_RX_BITRATE_SHIFT (0)
#define I2S1_RXBITRATE_RX_BITRATE_MASK (0x3f << I2S1_RXBITRATE_RX_BITRATE_SHIFT)
#define I2S1_RXBITRATE_RX_BITRATE(x) ((x) << I2S1_RXBITRATE_RX_BITRATE_SHIFT)

/* --- I2S0_TXMODE values --------------------------------------- */

/* TXCLKSEL: Clock source selection for the transmit bit clock divider */
#define I2S0_TXMODE_TXCLKSEL_SHIFT (0)
#define I2S0_TXMODE_TXCLKSEL_MASK (0x3 << I2S0_TXMODE_TXCLKSEL_SHIFT)
#define I2S0_TXMODE_TXCLKSEL(x) ((x) << I2S0_TXMODE_TXCLKSEL_SHIFT)

/* TX4PIN: Transmit 4-pin mode selection */
#define I2S0_TXMODE_TX4PIN_SHIFT (2)
#define I2S0_TXMODE_TX4PIN_MASK (0x1 << I2S0_TXMODE_TX4PIN_SHIFT)
#define I2S0_TXMODE_TX4PIN(x) ((x) << I2S0_TXMODE_TX4PIN_SHIFT)

/* TXMCENA: Enable for the TX_MCLK output */
#define I2S0_TXMODE_TXMCENA_SHIFT (3)
#define I2S0_TXMODE_TXMCENA_MASK (0x1 << I2S0_TXMODE_TXMCENA_SHIFT)
#define I2S0_TXMODE_TXMCENA(x) ((x) << I2S0_TXMODE_TXMCENA_SHIFT)

/* --- I2S1_TXMODE values --------------------------------------- */

/* TXCLKSEL: Clock source selection for the transmit bit clock divider */
#define I2S1_TXMODE_TXCLKSEL_SHIFT (0)
#define I2S1_TXMODE_TXCLKSEL_MASK (0x3 << I2S1_TXMODE_TXCLKSEL_SHIFT)
#define I2S1_TXMODE_TXCLKSEL(x) ((x) << I2S1_TXMODE_TXCLKSEL_SHIFT)

/* TX4PIN: Transmit 4-pin mode selection */
#define I2S1_TXMODE_TX4PIN_SHIFT (2)
#define I2S1_TXMODE_TX4PIN_MASK (0x1 << I2S1_TXMODE_TX4PIN_SHIFT)
#define I2S1_TXMODE_TX4PIN(x) ((x) << I2S1_TXMODE_TX4PIN_SHIFT)

/* TXMCENA: Enable for the TX_MCLK output */
#define I2S1_TXMODE_TXMCENA_SHIFT (3)
#define I2S1_TXMODE_TXMCENA_MASK (0x1 << I2S1_TXMODE_TXMCENA_SHIFT)
#define I2S1_TXMODE_TXMCENA(x) ((x) << I2S1_TXMODE_TXMCENA_SHIFT)

/* --- I2S0_RXMODE values --------------------------------------- */

/* RXCLKSEL: Clock source selection for the receive bit clock divider */
#define I2S0_RXMODE_RXCLKSEL_SHIFT (0)
#define I2S0_RXMODE_RXCLKSEL_MASK (0x3 << I2S0_RXMODE_RXCLKSEL_SHIFT)
#define I2S0_RXMODE_RXCLKSEL(x) ((x) << I2S0_RXMODE_RXCLKSEL_SHIFT)

/* RX4PIN: Receive 4-pin mode selection */
#define I2S0_RXMODE_RX4PIN_SHIFT (2)
#define I2S0_RXMODE_RX4PIN_MASK (0x1 << I2S0_RXMODE_RX4PIN_SHIFT)
#define I2S0_RXMODE_RX4PIN(x) ((x) << I2S0_RXMODE_RX4PIN_SHIFT)

/* RXMCENA: Enable for the RX_MCLK output */
#define I2S0_RXMODE_RXMCENA_SHIFT (3)
#define I2S0_RXMODE_RXMCENA_MASK (0x1 << I2S0_RXMODE_RXMCENA_SHIFT)
#define I2S0_RXMODE_RXMCENA(x) ((x) << I2S0_RXMODE_RXMCENA_SHIFT)

/* --- I2S1_RXMODE values --------------------------------------- */

/* RXCLKSEL: Clock source selection for the receive bit clock divider */
#define I2S1_RXMODE_RXCLKSEL_SHIFT (0)
#define I2S1_RXMODE_RXCLKSEL_MASK (0x3 << I2S1_RXMODE_RXCLKSEL_SHIFT)
#define I2S1_RXMODE_RXCLKSEL(x) ((x) << I2S1_RXMODE_RXCLKSEL_SHIFT)

/* RX4PIN: Receive 4-pin mode selection */
#define I2S1_RXMODE_RX4PIN_SHIFT (2)
#define I2S1_RXMODE_RX4PIN_MASK (0x1 << I2S1_RXMODE_RX4PIN_SHIFT)
#define I2S1_RXMODE_RX4PIN(x) ((x) << I2S1_RXMODE_RX4PIN_SHIFT)

/* RXMCENA: Enable for the RX_MCLK output */
#define I2S1_RXMODE_RXMCENA_SHIFT (3)
#define I2S1_RXMODE_RXMCENA_MASK (0x1 << I2S1_RXMODE_RXMCENA_SHIFT)
#define I2S1_RXMODE_RXMCENA(x) ((x) << I2S1_RXMODE_RXMCENA_SHIFT)

/**@}*/

#ifdef __cplusplus
}
#endif

#endif
