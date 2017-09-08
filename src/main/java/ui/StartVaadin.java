package ui;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Binder;
import com.vaadin.data.HasValue;
import com.vaadin.data.ValueProvider;
import com.vaadin.server.Setter;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

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
	
	@Override
	protected void init(VaadinRequest request) {
		String notification = this.dogadjajService.findDogadjajById(1).getNaziv();
	    setContent(new Button("Click me", e -> Notification.show(notification)));
	    
	    VerticalLayout verticalMain = new VerticalLayout();
	    
	    HorizontalLayout vertical = new HorizontalLayout();
	    Panel panel = new Panel("Filter gradova");
	    panel.setContent(vertical);
	    
	    dogadjaji = this.dogadjajService.findByCriteria(dq);
	    
	    Grid grid = new Grid<>(Dogadjaj.class);
	    grid.setItems(dogadjaji);
	    verticalMain.addComponent(grid);
	    
	    Panel panelData = new Panel();
	    verticalMain.addComponent(panelData);
	    
	    HorizontalLayout lay = new HorizontalLayout();
	    panelData.setContent(lay);
	    
	    TextField naziv = new TextField();
	    Binder binderNaziv = new Binder<>(DogadjajQuery.class);
	    binderNaziv.bind(naziv, DogadjajQuery::getNaziv, DogadjajQuery::setNaziv);
	    
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
	    
	    CheckBoxGroup<Velicinagrada> tipGrada = new CheckBoxGroup<Velicinagrada>("Izaberi tip grada");
	    tipGrada.setItems(this.velicinaGradaService.findAktivni());
	    tipGrada.setItemCaptionGenerator(Velicinagrada::getNaziv);
	    vertical.addComponent(tipGrada);
	    
	    ListSelect<Grad> gradovi = new ListSelect<Grad>("Izaberi grad");
	    gradovi.setItems(this.gradService.findAll());
	    gradovi.setItemCaptionGenerator(Grad::getNaziv);
	    vertical.addComponent(gradovi);
	    
	    gradovi.addSelectionListener(e -> {
	    	
	    	List<Grad> selectedGradoviList;
	    	if(e.getAllSelectedItems() == null || e.getAllSelectedItems().isEmpty())
	    		selectedGradoviList = this.gradService.findAll();
	    	else
	    	{
	    		Set<Grad> selectedGradoviSet = e.getAllSelectedItems();
	    	    selectedGradoviList = selectedGradoviSet.stream().collect(Collectors.toList()); 
	    	}
            dq.setGradovi(selectedGradoviList);
    	    dogadjaji = this.dogadjajService.findByCriteria(dq);
    	    grid.setItems(dogadjaji);
        });
	    
	    verticalMain.addComponent(panel);
	    this.setContent(verticalMain);
	}

}
