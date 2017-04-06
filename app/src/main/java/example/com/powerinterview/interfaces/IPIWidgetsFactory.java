package example.com.powerinterview.interfaces;

import android.content.Context;
import android.view.View;

import example.com.powerinterview.exceptions.FactoryException;

/**
 * Created by Игорь on 04.04.2017.
 */

public interface IPIWidgetsFactory  {


    View create(String className, Context context) throws FactoryException;

}
