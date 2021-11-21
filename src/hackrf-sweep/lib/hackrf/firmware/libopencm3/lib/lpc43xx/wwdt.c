/*
* This file is part of the libopencm3 project.
*
* Copyright (C) 2016 Dominic Spill <dominicgs@gmail.com>
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

#include <libopencm3/lpc43xx/wwdt.h>

void wwdt_reset(uint32_t timeout) {
    WWDT_MOD = WWDT_MOD_WDEN | WWDT_MOD_WDRESET;
    timeout &= 0xFFFFFF;
    WWDT_TC = timeout;
    WWDT_FEED_SEQUENCE;
}
