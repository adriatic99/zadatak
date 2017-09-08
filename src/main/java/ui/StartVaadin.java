package ui;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import models.Organizacijskajedinica;
import service.DogadjajService;
import service.OrganizacijskajedinicaService;

@SpringUI
@Theme("valo")
public class StartVaadin extends UI {

	@Autowired
	private DogadjajService dogadjajService;
	@Autowired
	private OrganizacijskajedinicaService oj;
	
	@Override
	protected void init(VaadinRequest request) {
		String notification = this.dogadjajService.findDogadjajById(1).getNaziv();
	    setContent(new Button("Click me", e -> Notification.show(notification)));
	    
	    VerticalLayout verticalMain = new VerticalLayout();
	    
	    HorizontalLayout vertical = new HorizontalLayout();
	    Panel panel = new Panel("Regije");
	    panel.setContent(vertical);
	    
	    CheckBoxGroup<Organizacijskajedinica> regije = new CheckBoxGroup<Organizacijskajedinica>
	    						("Izaberi regije");
	    List<Organizacijskajedinica> items = this.oj.getRegije();
	    regije.setItems(items);
	    regije.setItemCaptionGenerator(Organizacijskajedinica::getNaziv);
	    vertical.addComponent(regije);
	    
	    CheckBoxGroup<Organizacijskajedinica> zupanije = new CheckBoxGroup<Organizacijskajedinica>
		("Izaberi županije");
	    List<Organizacijskajedinica> selectedRegije = regije.getValue().stream().collect(Collectors.toList());
	    zupanije.setItems(this.oj.findByParentIn(selectedRegije));
	    zupanije.setItemCaptionGenerator(Organizacijskajedinica::getNaziv);
	    vertical.addComponent(zupanije);
	    
	    regije.addSelectionListener(e -> {

            Set<Organizacijskajedinica> selectedR = e.getAllSelectedItems();
    	    List<Organizacijskajedinica> selectedRegijeList = selectedR.stream().collect(Collectors.toList());
    	    List<Organizacijskajedinica> selectItemsZupanije = this.oj.findByParentIn(selectedRegijeList);
    	    zupanije.setItems(selectItemsZupanije);
        });
	    
	    verticalMain.addComponent(panel);
	    this.setContent(verticalMain);
	}

}
