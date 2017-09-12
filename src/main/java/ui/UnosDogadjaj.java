package ui;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.selection.SingleSelectionEvent;
import com.vaadin.event.selection.SingleSelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;

import models.Dogadjaj;
import service.DogadjajService;
import service.GradService;

@SpringView(name="unosDogadjaj")
public class UnosDogadjaj extends FormLayout implements View {

	Dogadjaj dogadjaj = new Dogadjaj();
	String dateFormat = "dd.MM.yyyy HH:mm";
	@Autowired
	private GradService gradService;
	@Autowired
	private DogadjajService dogadjajService;
	
	@PostConstruct
	protected void init() {
		
		Binder<Dogadjaj> binder = new Binder<>();
		
		TextField naziv = new TextField("Naziv");
	    binder.forField(naziv).asRequired("potrebno je unijeti naziv događaja")
	    		.withValidator(new StringLengthValidator(
	    		        "Naziv mora imati između 2 i 200 znakova",
	    		        2, 200))
	    		.bind(Dogadjaj::getNaziv, Dogadjaj::setNaziv);
	    binder.bindInstanceFields(dogadjaj);
	    binder.setBean(dogadjaj);	
	    
	    DateTimeField odVrijeme = new DateTimeField("Vrijeme Od:");
	    DateTimeField doVrijeme = new DateTimeField("Vrijeme Do:");
	    odVrijeme.setDateFormat(dateFormat);
	    doVrijeme.setDateFormat(dateFormat);
	    
	    binder.forField(odVrijeme)
	    	.asRequired("Dodaj vrijeme početka događaja")
	    	.bind(Dogadjaj::getOdVrijeme, Dogadjaj::setOdVrijeme);
	    
	    binder.forField(doVrijeme)
			.asRequired("Dodaj vrijeme završetka događaja")
			.bind(Dogadjaj::getDoVrijeme, Dogadjaj::setDoVrijeme);
		
	    RadioButtonGroup<String> optionSlobodanUlaz = new RadioButtonGroup("slobodan ulaz?");
	    List<String> items = Arrays.asList("Da","Ne");
	    optionSlobodanUlaz.setItems(items);
	    optionSlobodanUlaz.addSelectionListener(new SingleSelectionListener<String>() {

			@Override
			public void selectionChange(SingleSelectionEvent<String> event) {
				if(event.getSelectedItem() == null)
					return;
				else if(event.getSelectedItem().equals("Da"))
					dogadjaj.setSlobodanUlaz(true);
				else if(event.getSelectedItem().equals("Ne"))
					dogadjaj.setSlobodanUlaz(false);
			}
	    	
	    });
	    
	    ComboBox listGradovi = new ComboBox("Izaberi grad");
	    listGradovi.setItems(this.gradService.findAll());
	    binder.bind(listGradovi, Dogadjaj::getGrad, Dogadjaj::setGrad);
	    
	    this.addComponent(naziv);
	    this.addComponent(odVrijeme);
	    this.addComponent(doVrijeme);
	    this.addComponent(optionSlobodanUlaz);
	    this.addComponent(listGradovi);
	    
	    Button saveButton = new Button("Spremi događaj",
	    		  event -> {
	    		    try {
	    		      binder.writeBean(dogadjaj);
	    		      this.dogadjajService.save(dogadjaj);
	    		      dogadjaj = new Dogadjaj();
	    		      binder.readBean(dogadjaj);
	    		    } catch (ValidationException e) {
	    		      Notification.show("Događaj nije spremljen, " +
	    		        "došlo je do greške.");
	    		    }
	    		});
	    this.addComponent(saveButton);
	    
	    Button pretraziButton = new Button("Pretraži događaj");
	    pretraziButton.addClickListener(clickEvent ->
		    	this.getUI().getNavigator().navigateTo("searchDogadjaj"));
	    
	    this.addComponent(pretraziButton);
	    
	}
	
	@Override
    public void enter(ViewChangeEvent event) {
		System.out.println("Unos Događaj");
    }

}
