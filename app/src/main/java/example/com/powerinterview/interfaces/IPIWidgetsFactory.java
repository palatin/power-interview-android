package example.com.powerinterview.interfaces;

import android.content.Context;
import android.view.View;

import java.util.List;

import example.com.powerinterview.exceptions.FactoryException;
import example.com.powerinterview.model.Attribute;
import example.com.powerinterview.model.Widget;

/**
 * Created by Игорь on 04.04.2017.
 */

public interface IPIWidgetsFactory  {


    IWidget create(Widget widget, Context context) throws FactoryException;



}
