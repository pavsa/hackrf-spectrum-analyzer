package jspectrumanalyzer.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class FrequencySelectorPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -4830755053319335365L;
	private int					minValue, maxValue, step, intialValue;
	private JTextField			textField_val1;
	private JTextField			textField_val2;
	private JTextField			textField_val3;

	private JTextField			textField_val4;
	private int					value				= 0;

	/**
	 * Create the panel.
	 */
	public FrequencySelectorPanel(int minValue, int maxValue, int step, int intialValue)
	{
		setBackground(Color.BLACK);
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.step = step;

		setLayout(new GridLayout(0, 4, 0, 0));

		JButton button_plus4 = new JButton("+");
		button_plus4.setBackground(Color.BLACK);
		add(button_plus4);

		JButton button_plus3 = new JButton("+");
		button_plus3.setBackground(Color.BLACK);
		add(button_plus3);

		JButton button_plus2 = new JButton("+");
		button_plus2.setBackground(Color.BLACK);
		add(button_plus2);

		JButton button_plus1 = new JButton("+");
		button_plus1.setBackground(Color.BLACK);
		add(button_plus1);

		Font fontField = new Font(Font.MONOSPACED, Font.BOLD, 16);

		textField_val4 = new JTextField();
		textField_val4.setFont(fontField);
		textField_val4.setHorizontalAlignment(SwingConstants.CENTER);
		textField_val4.setEditable(false);
		add(textField_val4);
		textField_val4.setColumns(1);
		textField_val4.setBorder(null);

		textField_val3 = new JTextField();
		textField_val3.setFont(fontField);
		textField_val3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_val3.setEditable(false);
		add(textField_val3);
		textField_val3.setColumns(1);
		textField_val3.setBorder(null);

		textField_val2 = new JTextField();
		textField_val2.setFont(fontField);
		textField_val2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_val2.setEditable(false);
		add(textField_val2);
		textField_val2.setColumns(1);
		textField_val2.setBorder(null);

		textField_val1 = new JTextField();
		textField_val1.setText("0");
		textField_val1.setFont(fontField);
		textField_val1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_val1.setEditable(false);
		add(textField_val1);
		textField_val1.setColumns(1);
		textField_val1.setBorder(null);

		JButton button_minus4 = new JButton("-");
		button_minus4.setBackground(Color.BLACK);
		add(button_minus4);

		JButton button_minus3 = new JButton("-");
		button_minus3.setBackground(Color.BLACK);
		add(button_minus3);

		JButton button_minus2 = new JButton("-");
		button_minus2.setBackground(Color.BLACK);
		add(button_minus2);

		JButton button_minus1 = new JButton("-");
		button_minus1.setBackground(Color.BLACK);
		add(button_minus1);

		button_plus1.addActionListener(addListener(true, 1));
		button_plus2.addActionListener(addListener(true, 2));
		button_plus3.addActionListener(addListener(true, 3));
		button_plus4.addActionListener(addListener(true, 4));

		button_minus1.addActionListener(addListener(false, 1));
		button_minus2.addActionListener(addListener(false, 2));
		button_minus3.addActionListener(addListener(false, 3));
		button_minus4.addActionListener(addListener(false, 4));

		Dimension d = new Dimension(175, 65);
		setPreferredSize(d);
		setMaximumSize(d);
		setMinimumSize(d);

		setValue(intialValue);
	}

	public int getValue()
	{
		return value;
	}

	/**
	 * Sets a new value
	 * @param newValue
	 * @return true if value was valid and set, false otherwise
	 */
	public boolean setValue(int newValue)
	{
		if (newValue < minValue || newValue > maxValue)
			return false;
		int oldValue = this.value;
		if (!SwingUtilities.isEventDispatchThread())
		{
			try
			{
				SwingUtilities.invokeAndWait(() -> {
					try
					{
						fireValueChange(oldValue, newValue);
					}
					catch (PropertyVetoException e)
					{
						throw new RuntimeException(e);
					}
				});
			}
			catch (InvocationTargetException e)
			{
				return false;
			}
			catch (InterruptedException e)
			{
				return false;
			}
			return true;
		}
		else
		{
			try
			{
				fireValueChange(oldValue, newValue);
				return true;
			}
			catch (PropertyVetoException e)
			{
				return false;
			}
		}
	}

	private void add(int digit)
	{
		int newValue = value + getMultiplier(digit);
		if (newValue < minValue)
			newValue = minValue;
		if (newValue > maxValue)
			newValue = maxValue;
		setValue(newValue);
	}

	private ActionListener addListener(boolean add, int digit)
	{
		ActionListener listener = e -> {
			if (add)
				add(digit);
			else
				subtract(digit);
		};
		return listener;
	}

	private void fireValueChange(int oldValue, int newValue) throws PropertyVetoException
	{
		fireVetoableChange("value", oldValue, newValue);
		FrequencySelectorPanel.this.value = newValue;
		int digit1 = (newValue % 10);
		int digit2 = ((newValue / 10) % 10);
		int digit3 = ((newValue / 100) % 10);
		int digit4 = ((newValue / 1000) % 10);
		textField_val1.setText(digit1 + "");
		textField_val2.setText(digit4 == 0 && digit3 == 0 && digit2 == 0 ? " " : digit2 + "");
		textField_val3.setText(digit4 == 0 && digit3 == 0 ? " " : digit3 + "");
		textField_val4.setText(digit4 == 0 ? "" : digit4 + "");
		firePropertyChange("value", oldValue, newValue);
	}

	private int getMultiplier(int digit)
	{
		int multiplier = 1;
		for (int i = 0; i < digit - 1; i++)
		{
			multiplier *= 10;
		}
		return multiplier;
	}

	private void subtract(int digit)
	{
		int newValue = value - getMultiplier(digit);
		if (newValue < minValue)
			newValue = minValue;
		if (newValue > maxValue)
			newValue = maxValue;
		setValue(newValue);
	}
}
