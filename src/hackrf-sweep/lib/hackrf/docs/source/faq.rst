.. _faq:

================================================
FAQ
================================================


What is the minimum signal power level that can be detected by HackRF?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

This isn't a question that can be answered for a general purpose SDR platform such as HackRF. Any answer would be very specific to a particular application. For example, an answerable question might be: What is the minimum power level in dBm of modulation M at frequency F that can be detected by HackRF One with software S under configuration C at a bit error rate of no more than E%? Changing any of those variables (M, F, S, C, or E) would change the answer to the question. Even a seemingly minor software update might result in a significantly different answer. To learn the exact answer for a specific application, you would have to measure it yourself.

HackRF's concrete specifications include operating frequency range, maximum sample rate, and dynamic range in bits. These specifications can be used to roughly determine the suitability of HackRF for a given application. Testing is required to finely measure performance in an application. Performance can typically be enhanced significantly by selecting an appropriate antenna, external amplifier, and/or external filter for the application.


----


Is HackRF full-duplex?
~~~~~~~~~~~~~~~~~~~~~~

HackRF One is a half-duplex transceiver. This means that it can transmit or receive but not both at the same time.


----


Why isn't HackRF One full-duplex?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

HackRF One is designed to support the widest possible range of SDR applications in a single, low cost, portable device. Many applications do not require full-duplex operation. Full-duplex support would have made HackRF larger and more expensive, and it would have required an external power supply. Since full-duplex needs can be met by simply using a second HackRF One, it made sense to keep the device small, portable, and low cost for everyone who does not require full-duplex operation.


----


How could the HackRF One design be changed to make it full-duplex?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

The HackRF One hardware design is actually full-duplex (at lower sample rates) from the USB connection through the ADC/DAC. The RF section is the only part of the design that cannot support full-duplex operation. The easiest way to make HackRF One full-duplex would be to create an add-on board that duplicates the RF section and also provides an external power input (from a wall wart, for example) for the additional power required. This would also require software effort; the firmware, CPLD, libhackrf, and other host software would all need work to support full-duplex operation.

If you were to try to redesign the RF section on HackRF One to support full-duplex, the main thing to focus on would be the MAX2837 (intermediate frequency transceiver). This part is half-duplex, so you would either need two of them or you would have to redesign the RF section to use something other than the MAX2837, likely resulting in a radically different design. If you used two MAX2837s you might be able to use one RFFC5071 instead of two RFFC5072s.


----


.. _bigspike:

What is the big spike in the center of my received spectrum?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

If you see a large spike in the center of your FFT display regardless of the frequenecy you are tuned to, you are seeing a DC offset (or component or bias). The term "DC" comes from "Direct Current" in electronics. It is the unchanging aspect of a signal as opposed to the "alternating" part of the signal (AC) that changes over time. Take, for example, the signal represented by the digital sequence:

.. code-block:: sh

	-2, -1, 1, 6, 8, 9, 8, 6, 1, -1, -2, -1, 1, 6, 8, 9, 8, 6, 1, -1, -2, -1, 1, 6, 8, 9, 8, 6, 1, -1

This periodic signal contains a strong sinusoidal component spanning from -2 to 9. If you were to plot the spectrum of this signal, you would see one spike at the frequency of this sinusoid and a second spike at 0 Hz (DC). If the signal spanned from values -2 to 2 (centered around zero), there would be no DC offset. Since it is centered around 3.5 (the number midway between -2 and 9), there is a DC component.

Samples produced by HackRF are measurements of radio waveforms, but the measurement method is prone to a DC bias introduced by HackRF. It's an artifact of the measurement system, not an indication of a received radio signal. DC offset is not unique to HackRF; it is common to all quadrature sampling systems.

There was a bug in the HackRF firmware (through release 2013.06.1) that made the DC offset worse than it should have been. In the worst cases, certain Jawbreakers experienced a DC offset that drifted to a great extreme over several seconds of operation. This bug has been fixed. The fix reduces DC offset but does not do away with it entirely. It is something you have to live with when using any quadrature sampling system like HackRF.

A high DC offset is also one of a few symptoms that can be caused by a software version mismatch. A common problem is that people run an old version of gr-osmosdr with newer firmware.


----


What gain controls are provided by HackRF?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

HackRF (both Jawbreaker and One) provides three different analog gain controls on RX and two on TX. The three RX gain controls are at the RF ("amp", 0 or 14 dB), IF ("lna", 0 to 40 dB in 8 dB steps), and baseband ("vga", 0 to 62 dB in 2 dB steps) stages. The two TX gain controls are at the RF (0 or 14 dB) and IF (0 to 47 dB in 1 dB steps) stages.


----


Why is the RF gain setting restricted to two values?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

HackRF has two RF amplifiers close to the antenna port, one for TX and one for RX. These amplifiers have two settings: on or off. In the off state, the amps are completely bypassed. They nominally provide 14 dB of gain when on, but the actual amount of gain varies by frequency. In general, expect less gain at higher frequencies. For fine control of gain, use the IF and/or baseband gain options.


----


Why are the LEDs on HackRF different colours?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Each LED is a single color. There are no multi-colored LEDs on HackRF One. Adjacent LEDs are different colors in order to make them easier to distinguish from one another. The colors do not mean anything.


----


Where can I purchase HackRF?
~~~~~~~~~~~~~~~~~~~~~~~~~~~~

HackRF is designed and manufactured by Great Scott Gadgets. We do not sell low volumes of HackRFs to people individually; instead we have agreements with specific resellers. Please see our reseller list on the Great Scott Gadgets website for availability: `http://greatscottgadgets.com/hackrf/ <http://greatscottgadgets.com/hackrf/>`__. 

HackRF is open source hardware, so you can also build your own.
