package shared.mvc;

import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import shared.mvc.ModelValue.ModelValueBoolean;
import shared.mvc.ModelValue.ModelValueInt;

/**
 * In MVC model, this class is the Controller, listening for change events from
 * both the model and the view and ensuring their states are synchronized.
 * 
 * @param <T>
 */
public class MVCController {
	public static interface ViewAddChangeListener<T> {
		public void addChangeListener(Consumer<T> viewValueChangedCall);
	}

	/**
	 * Flag indicating the view is being updated to prevent its listeners from
	 * changing the {@link ModelValue} causing an infinite loop.
	 */
	protected boolean disableViewListeners = false;

	public <T> MVCController(ViewAddChangeListener<T> viewAddChangeListener, Consumer<T> viewSetValue,
			ModelValue<T> model) {
		this(viewAddChangeListener, viewSetValue, model, (T val) -> val, (T val) -> val);
	}

	/**
	 * Generic ({@link JComponent} independent) constructor
	 * 
	 * @param viewAddChangeListener
	 * @param viewSetValue
	 * @param model
	 * @param conversionViewToModel
	 * @param conversionModelToView
	 */
	public <VIEW_T, MODEL_T> MVCController(ViewAddChangeListener<VIEW_T> viewAddChangeListener,
			Consumer<VIEW_T> viewSetValue, ModelValue<MODEL_T> model, Function<VIEW_T, MODEL_T> conversionViewToModel,
			Function<MODEL_T, VIEW_T> conversionModelToView) {
		viewAddChangeListener.addChangeListener((VIEW_T newViewValue) -> updateModelValue(
				() -> model.setValue(conversionViewToModel.apply(newViewValue))));
		model.addListener(() -> updateView(() -> viewSetValue.accept(conversionModelToView.apply(model.getValue()))));
		sync(model);
	}

	public MVCController(JCheckBox checkbox, ModelValueBoolean model) {
		checkbox.addChangeListener((ChangeEvent e) -> updateModelValue(() -> model.setValue(checkbox.isSelected())));
		model.addListener(() -> updateView(() -> checkbox.setSelected(model.getValue())));
		sync(model);
	}

	public MVCController(JSlider slider, ModelValueInt model) {
		this(slider, model, val -> val, val -> val);
	}

	public <T> MVCController(JSlider slider, ModelValue<T> model, Function<Integer, T> conversionViewToModel,
			Function<T, Integer> conversionModelToView) {
		slider.addChangeListener((ChangeEvent e) -> updateModelValue(
				() -> model.setValue(conversionViewToModel.apply(slider.getValue()))));
		model.addListener(() -> updateView(() -> slider.setValue(conversionModelToView.apply(model.getValue()))));
		sync(model);
	}

	public <T> MVCController(JSpinner spinner, ModelValue<T> model, Function<Object, T> conversionViewToModel,
			Function<T, Object> conversionModelToView) {
		spinner.addChangeListener((ChangeEvent e) -> updateModelValue(
				() -> model.setValue(conversionViewToModel.apply(spinner.getValue()))));
		model.addListener(() -> updateView(() -> spinner.setValue(conversionModelToView.apply(model.getValue()))));
		sync(model);
	}

	public <T> MVCController(JComboBox<T> comboBox, ModelValue<T> model, Function<Object, T> conversionViewToModel,
			Function<T, Object> conversionModelToView) {
		comboBox.addActionListener(e -> updateModelValue(() -> model.setValue(conversionViewToModel.apply(comboBox.getSelectedItem()))));
		model.addListener(() -> updateView(() -> comboBox.setSelectedItem(conversionModelToView.apply(model.getValue()))));
		sync(model);
	}

	public <T> MVCController(JComboBox<T> comboBox, ModelValue<T> model) {
		this(comboBox, model, val -> (T)val, val -> val);
	}

	/**
	 * syncs values with the component
	 */
	protected void sync(ModelValue<?> val) {
		val.callObservers();
	}

	/**
	 * Makes sure the when the model's value is updated, the view's listeners
	 * are not triggered, causing an infinite loop.
	 * 
	 * @param viewChangedCall
	 */
	protected void updateView(Runnable viewChangedCall) {
		/**
		 * must be run in swing's event loop thread.
		 */
		SwingUtilities.invokeLater(() -> {
			disableViewListeners = true;
			try {
				viewChangedCall.run();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				disableViewListeners = false;
			}
		});
	}

	protected void _updateModelValueCall(Runnable updateModelValueCall) {
		if (!disableViewListeners)
			updateModelValueCall.run();
	}
	
	protected void updateModelValue(Runnable updateModelValueCall) {
		if (!SwingUtilities.isEventDispatchThread()) {
			SwingUtilities.invokeLater(() -> {
				_updateModelValueCall(updateModelValueCall);
			});
		} else {
			_updateModelValueCall(updateModelValueCall);
		}
	}

}