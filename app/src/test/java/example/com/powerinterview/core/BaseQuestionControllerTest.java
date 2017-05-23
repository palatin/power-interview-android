package example.com.powerinterview.core;

import android.content.Context;
import android.view.View;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import example.com.powerinterview.exceptions.EmptyQuestionException;
import example.com.powerinterview.interfaces.IPIWidgetsFactory;
import example.com.powerinterview.interfaces.QuestionController;
import example.com.powerinterview.interfaces.Widget;
import example.com.powerinterview.model.Question;
import example.com.powerinterview.model.WidgetEntity;
import example.com.powerinterview.widgets.PIBootstrapButton;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Игорь on 23.05.2017.
 */
public class BaseQuestionControllerTest {

    IPIWidgetsFactory factory = mock(IPIWidgetsFactory.class);
    QuestionController controller = new BaseQuestionController(factory);

    @Test
    public void parseQuestion() throws Exception {

        Question question = new Question();
        List<WidgetEntity> widgetEntities = new ArrayList<>();
        widgetEntities.add(new WidgetEntity());
        widgetEntities.add(new WidgetEntity());
        question.setWidgetEntities(widgetEntities);
        Widget widget = mock(PIBootstrapButton.class);
        View view = new View(mock(Context.class));
        when(widget.getView()).thenReturn(view);
        when(factory.create(any(WidgetEntity.class), any(Context.class))).thenReturn(widget);
        View[] views = controller.parseQuestion(question, mock(Context.class));
        assertEquals(view, views[0]);
        assertEquals(view, views[1]);
        verify(factory, times(2)).create(any(WidgetEntity.class), any(Context.class));

    }

    @Test
    public void parseQuestionWithEmptyWidgets() throws Exception {

        Question question = new Question();
        List<WidgetEntity> widgetEntities = new ArrayList<>();
        question.setWidgetEntities(widgetEntities);
        Widget widget = mock(PIBootstrapButton.class);
        View view = new View(mock(Context.class));
        when(widget.getView()).thenReturn(view);
        when(factory.create(any(WidgetEntity.class), any(Context.class))).thenReturn(widget);
        View[] views = controller.parseQuestion(question, mock(Context.class));
        assertEquals(views.length, 0);
        verify(factory, never()).create(any(WidgetEntity.class), any(Context.class));

    }

    @Test(expected = EmptyQuestionException.class)
    public void parseQuestionWithNullWidgets() throws Exception {

        Question question = new Question();

        View[] views = controller.parseQuestion(question, mock(Context.class));

    }

}