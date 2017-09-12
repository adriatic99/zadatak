package ui;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import models.Dogadjaj;
import models.DogadjajQuery;
import service.DogadjajService;
import service.GradService;
import service.OrganizacijskajedinicaService;
import service.VelicinaGradaService;

@SpringUI
@Theme("valo")
public class StartVaadin extends UI {

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
	    Button pretraziButton = new Button("Pretraži događaj");
	    pretraziButton.addClickListener(clickEvent ->
		    	this.getUI().getNavigator().navigateTo("searchDogadjaj"));   
	    root.addComponent(pretraziButton);
	    
	    Button unos = new Button("Unesi dogadjaj");
		unos.addClickListener(clickEvent ->
	    	this.getUI().getNavigator().navigateTo("unosDogadjaj"));
		root.addComponent(unos);
	    
	    root.addComponent(viewContainer);
	}

}
