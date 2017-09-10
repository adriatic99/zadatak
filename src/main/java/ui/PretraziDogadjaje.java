package ui;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.renderers.TextRenderer;

import models.Dogadjaj;
import models.DogadjajQuery;
import models.Grad;
import models.Organizacijskajedinica;
import models.Velicinagrada;
import service.DogadjajService;
import service.GradService;
import service.OrganizacijskajedinicaService;
import service.VelicinaGradaService;

@SpringView(name="searchDogadjaj")
public class PretraziDogadjaje extends VerticalLayout implements View {
	
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
	Grid<Dogadjaj> grid = new Grid<>(Dogadjaj.class);
	
	public PretraziDogadjaje()
	{
		setSizeFull();
		this.setMargin(true);
		String notification = "notification";
	    addComponent(new Button("Click me", e -> Notification.show(notification)));
	    this.getUI().getNavigator().navigateTo("unosDogadjaj");
	    dogadjaji = this.dogadjajService.findByCriteria(dq);
	    
	    this.panelGrid();
	    this.panelData();
	}
	
	//Panel: grid
	private void panelGrid()
	{
		 grid.setItems(dogadjaji);
		 grid.removeColumn("grad");
	     grid.removeColumn("slobodanUlaz");
		 grid.removeColumn("slobodanUlazString");
		 Column<Dogadjaj, String> columnSU = grid.addColumn(Dogadjaj::getSlobodanUlazString);
		 columnSU.setRenderer(new TextRenderer());
		 Column<Dogadjaj, Grad> column = grid.addColumn(Dogadjaj::getGrad);
		 column.setRenderer(
		    grad -> grad.getNaziv(),
			new TextRenderer());
		 column.setCaption("Grad");
		 columnSU.setCaption("slobodan ulaz?");
		 grid.setColumnOrder("id", "naziv", "odVrijeme");
		 this.addComponent(grid);
	}
	
	//Panel: Osnovni podaci naziv, vrijeme...
	public void panelData()
	{
		Panel panelData = new Panel();
	    this.addComponent(panelData);
	    
	    HorizontalLayout lay = new HorizontalLayout();
	    panelData.setContent(lay);
	    
	    TextField naziv = new TextField("Naziv");
	    Binder<DogadjajQuery> binder = new Binder<>();
	    binder.bind(naziv, DogadjajQuery::getNaziv, DogadjajQuery::setNaziv);
	    binder.bindInstanceFields(dq);
	    binder.setBean(dq);
	    binder.addStatusChangeListener(e -> {
    	    dogadjaji = this.dogadjajService.findByCriteria(dq);
    	    grid.setItems(dogadjaji);
	    });
	    lay.addComponent(naziv);
	    
	    Panel vrijemePanel = new Panel();
	    VerticalLayout vrijemeLayout = new VerticalLayout();
	    vrijemePanel.setContent(vrijemeLayout);
	    lay.addComponent(vrijemePanel);
	    DateTimeField dateOdVrijemePocetak = new DateTimeField("vrijeme početka događaja, od:");
	    binder.forField(dateOdVrijemePocetak).
	    	bind(DogadjajQuery::getOdVrijemePocetak, 
	    			DogadjajQuery::setOdVrijemePocetak);
	    dateOdVrijemePocetak.setDateFormat("dd.MM.yyyy HH:mm");
	    dateOdVrijemePocetak.setPlaceholder("dd.MM.yyyy HH:mm");
	    vrijemeLayout.addComponent(dateOdVrijemePocetak);
	    
	    DateTimeField dateOdVrijemeKraj = new DateTimeField("vrijeme početka događaja, do:");
	    binder.bind(dateOdVrijemeKraj, DogadjajQuery::getOdVrijemeKraj, DogadjajQuery::setOdVrijemeKraj);
	    dateOdVrijemeKraj.setDateFormat("dd.MM.yyyy HH:mm");
	    dateOdVrijemeKraj.setPlaceholder("dd.MM.yyyy HH:mm");
	    vrijemeLayout.addComponent(dateOdVrijemeKraj);
	    
	    DateTimeField dateDoVrijemePocetak = new DateTimeField("vrijeme kraja događaja, od:");
	    binder.bind(dateDoVrijemePocetak, DogadjajQuery::getDoVrijemePocetak, DogadjajQuery::setDoVrijemePocetak);
	    dateDoVrijemePocetak.setDateFormat("dd.MM.yyyy HH:mm");
	    dateDoVrijemePocetak.setPlaceholder("dd.MM.yyyy HH:mm");
	    vrijemeLayout.addComponent(dateDoVrijemePocetak);
	    
	    DateTimeField dateDoVrijemeKraj = new DateTimeField("vrijeme kraja događaja, do:");
	    binder.bind(dateDoVrijemeKraj, DogadjajQuery::getDoVrijemeKraj, DogadjajQuery::setDoVrijemeKraj);
	    dateDoVrijemeKraj.setDateFormat("dd.MM.yyyy HH:mm");
	    dateDoVrijemeKraj.setPlaceholder("dd.MM.yyyy HH:mm");
	    vrijemeLayout.addComponent(dateDoVrijemeKraj);
	    
	    RadioButtonGroup<String> slobodanUlazRadio =
	    	    new RadioButtonGroup<>("Slobodan ulaz?");
	    slobodanUlazRadio.setItems("Da", "Ne", "Nevažno");
	    binder.bind(slobodanUlazRadio, DogadjajQuery::getSlobodanUlazString, DogadjajQuery::setSlobodanUlazString);
	    lay.addComponent(slobodanUlazRadio);
	}
	
	//Panel: regije, županije, gradovi...
	public void panelGradovi()
	{
		VerticalLayout vertical = new VerticalLayout();
		Panel panel = new Panel();
		panel.setContent(vertical);
		this.addComponent(panel);
		
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
		
		CheckBoxGroup<Velicinagrada> tipGrada = new CheckBoxGroup<Velicinagrada>("Izaberi tip grada");
		tipGrada.setItems(this.velicinaGradaService.findAktivni());
		tipGrada.setItemCaptionGenerator(Velicinagrada::getNaziv);
		vertical.addComponent(tipGrada);
		
		ListSelect<Grad> gradovi = new ListSelect<Grad>("Izaberi grad");
		gradovi.setItems(this.gradService.findAll());
		gradovi.setItemCaptionGenerator(Grad::getNaziv);
		vertical.addComponent(gradovi);
		
		regije.addSelectionListener(e -> {
		
			Set<Organizacijskajedinica> selectedRegijeSet = e.getAllSelectedItems();
			List<Organizacijskajedinica> selectedRegijeList = selectedRegijeSet.stream().collect(Collectors.toList());
			List<Organizacijskajedinica> selectItemsZupanije = this.oj.findByParentIn(selectedRegijeList);
			zupanije.setItems(selectItemsZupanije);
		
			if(zupanije.getSelectedItems() == null || zupanije.getSelectedItems().isEmpty())
		{
				List<Organizacijskajedinica> selectedZupanijeList = null;
			List<Velicinagrada> selectedVelicinaGradaSelectedList = null;
		
			selectedZupanijeList = selectItemsZupanije;
		
			Set<Velicinagrada> setVelicinaGradaSelected = tipGrada.getSelectedItems();
			if(setVelicinaGradaSelected != null && !setVelicinaGradaSelected.isEmpty())
				selectedVelicinaGradaSelectedList = setVelicinaGradaSelected.stream().collect(Collectors.toList());
		
			if(selectedZupanijeList == null && selectedVelicinaGradaSelectedList == null)
				gradovi.setItems(this.gradService.findAll());
			else if(selectedZupanijeList == null)
				gradovi.setItems(this.gradService.findByVelicinaGradaIn(selectedVelicinaGradaSelectedList));
			else if(selectedVelicinaGradaSelectedList == null)
				gradovi.setItems(this.gradService.findByOrganizacijskaJedinicaIn(selectedZupanijeList));
			else
				gradovi.setItems(this.gradService.findByOrganizacijskaJedinicaInAndVelicinaGradaIn(
						selectedZupanijeList,
						selectedVelicinaGradaSelectedList));
		}
		});
		
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
		
		zupanije.addSelectionListener(e -> {
		
			List<Organizacijskajedinica> selectedZupanijeList = null;
			List<Velicinagrada> selectedVelicinaGradaSelectedList = null;
		
			Set<Organizacijskajedinica> selectedZupanije = e.getAllSelectedItems();
			if(selectedZupanije != null && !selectedZupanije.isEmpty())
				selectedZupanijeList = selectedZupanije.stream().collect(Collectors.toList());
			Set<Velicinagrada> setVelicinaGradaSelected = tipGrada.getSelectedItems();
			if(setVelicinaGradaSelected != null && !setVelicinaGradaSelected.isEmpty())
				selectedVelicinaGradaSelectedList = setVelicinaGradaSelected.stream().collect(Collectors.toList());
		
			if(selectedZupanijeList == null && selectedVelicinaGradaSelectedList == null)
				gradovi.setItems(this.gradService.findAll());
			else if(selectedZupanijeList == null)
				gradovi.setItems(this.gradService.findByVelicinaGradaIn(selectedVelicinaGradaSelectedList));
			else if(selectedVelicinaGradaSelectedList == null)
				gradovi.setItems(this.gradService.findByOrganizacijskaJedinicaIn(selectedZupanijeList));
			else
				gradovi.setItems(this.gradService.findByOrganizacijskaJedinicaInAndVelicinaGradaIn(
						selectedZupanijeList,
						selectedVelicinaGradaSelectedList));
		});
		
		tipGrada.addSelectionListener(e -> {
		
		List<Organizacijskajedinica> selectedZupanijeList = null;
		List<Velicinagrada> selectedVelicinaGradaSelectedList = null;
		
		Set<Organizacijskajedinica> selectedZupanije = zupanije.getSelectedItems();
		if(selectedZupanije != null && !selectedZupanije.isEmpty())
		selectedZupanijeList = selectedZupanije.stream().collect(Collectors.toList());
		Set<Velicinagrada> setVelicinaGradaSelected = tipGrada.getSelectedItems();
		if(setVelicinaGradaSelected != null && !setVelicinaGradaSelected.isEmpty())
		selectedVelicinaGradaSelectedList = setVelicinaGradaSelected.stream().collect(Collectors.toList());
		
		if(selectedZupanijeList == null && selectedVelicinaGradaSelectedList == null)
		gradovi.setItems(this.gradService.findAll());
		else if(selectedZupanijeList == null)
		gradovi.setItems(this.gradService.findByVelicinaGradaIn(selectedVelicinaGradaSelectedList));
		else if(selectedVelicinaGradaSelectedList == null)
		gradovi.setItems(this.gradService.findByOrganizacijskaJedinicaIn(selectedZupanijeList));
		else
		gradovi.setItems(this.gradService.findByOrganizacijskaJedinicaInAndVelicinaGradaIn(
		selectedZupanijeList,
		selectedVelicinaGradaSelectedList));
		});
		
		this.addComponent(panel);
	}
	
	private void selectGradove(CheckBoxGroup<Organizacijskajedinica> regije,
			CheckBoxGroup<Organizacijskajedinica> zupanije,
			CheckBoxGroup<Velicinagrada> tipGrada,
			ListSelect<Grad> gradovi)
	{
		Set<Organizacijskajedinica> selectedRegijeSet = regije.getSelectedItems();
		Set<Organizacijskajedinica> selectedZupanijeSet = zupanije.getSelectedItems();
		Set<Velicinagrada> selectedVelicinaGRadaSet = tipGrada.getSelectedItems();
		Set<Grad> selectedGradSet = gradovi.getSelectedItems();
	}
	
	//Panel: Button
	public void panelButtons()
	{
		Panel panel = new Panel();
		HorizontalLayout layout = new HorizontalLayout();
		panel.setContent(layout);
		this.addComponent(panel);
		
		Button ponisti = new Button("Poništi");
		ponisti.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				dq.ponisti();
			}
		});
		
		Button unos = new Button("Unesi događaji");
		unos.addClickListener(clickEvent ->
	    	this.getUI().getNavigator().navigateTo("unosDogadjaj"));
	}
	
	@Override
    public void enter(ViewChangeEvent event) {
		
    }
}
