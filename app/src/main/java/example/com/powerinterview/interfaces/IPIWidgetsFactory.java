package example.com.powerinterview.interfaces;

import android.content.Context;

import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.model.WidgetEntity;

/**
 * Created by Игорь on 04.04.2017.
 */

public interface IPIWidgetsFactory  {


    Widget create(WidgetEntity widgetEntity, Context context) throws FactoryException;



}
