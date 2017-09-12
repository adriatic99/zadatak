package ui;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Binder;
import com.vaadin.data.Converter;
import com.vaadin.data.HasValue;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.data.ValueProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Setter;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.TextRenderer;

import models.Organizacijskajedinica;
import models.Velicinagrada;
import models.Dogadjaj;
import models.DogadjajQuery;
import models.Grad;
import service.DogadjajService;
import service.GradService;
import service.OrganizacijskajedinicaService;
import service.VelicinaGradaService;

@SpringUI
@Theme("valo")
public class StartVaadin extends UI {

	@Autowired
	private DogadjajService dogadjajService;
	@Autowired
	private OrganizacijskajedinicaService oj;
	@Autowired
	private VelicinaGradaService velicinaGradaService;
	@Autowired
	private GradService gradService;
	private DogadjajQuery dq = new DogadjajQuery();
	List<Dogadjaj> dogadjaji;
	Navigator navigator = this.getUI().getNavigator();
	@Autowired
    private SpringViewProvider viewProvider;
	
	@Override
	protected void init(VaadinRequest request) {
		
		final Panel viewContainer = new Panel();
        viewContainer.setSizeFull();
	    Navigator navigator = new Navigator(this, viewContainer);
	    final VerticalLayout root = new VerticalLayout();
	    this.setContent(root);
	    navigator.addProvider(viewProvider);
	    navigator.navigateTo("searchDogadjaj");
	    Button button = new Button("search");
	    
	    root.addComponent(viewContainer);
	    root.addComponent(button);
        // If you didn't choose Java 8 when creating the project, convert this to an anonymous listener class
        button.addClickListener(event -> getUI().getNavigator().navigateTo("searchDogadjaj"));
	}

}
