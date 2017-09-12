package ui;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Binder;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinRequest;
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
	CheckBoxGroup<Organizacijskajedinica> regije;
	CheckBoxGroup<Organizacijskajedinica> zupanije;
	CheckBoxGroup<Velicinagrada> tipGrada;
	ListSelect<Grad> gradovi;
	
	public PretraziDogadjaje()
	{
		setSizeFull();
		this.setMargin(true);
	    
	}
	
	 @PostConstruct
	 void init() 
	 {
		 dogadjaji = this.dogadjajService.findByCriteria(dq);
		    
		 this.panelButtons();
		 this.panelGrid();
		 this.panelData();
	     this.panelGradovi();
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
		HorizontalLayout vertical = new HorizontalLayout();
		Panel panel = new Panel();
		panel.setContent(vertical);
		this.addComponent(panel);
		
		regije = new CheckBoxGroup<Organizacijskajedinica>
			("Izaberi regije");
		List<Organizacijskajedinica> items = this.oj.getRegije();
		regije.setItems(items);
		regije.setItemCaptionGenerator(Organizacijskajedinica::getNaziv);
		vertical.addComponent(regije);
		
		zupanije = new CheckBoxGroup<Organizacijskajedinica>
			("Izaberi županije");
		List<Organizacijskajedinica> selectedRegije = regije.getValue().stream().collect(Collectors.toList());
		zupanije.setItems(this.oj.findByParentIn(selectedRegije));
		zupanije.setItemCaptionGenerator(Organizacijskajedinica::getNaziv);
		vertical.addComponent(zupanije);
		
		tipGrada = new CheckBoxGroup<Velicinagrada>("Izaberi tip grada");
		tipGrada.setItems(this.velicinaGradaService.findAktivni());
		tipGrada.setItemCaptionGenerator(Velicinagrada::getNaziv);
		vertical.addComponent(tipGrada);
		
		gradovi = new ListSelect<Grad>("Izaberi grad");
		gradovi.setItems(this.gradService.findAll());
		gradovi.setItemCaptionGenerator(Grad::getNaziv);
		vertical.addComponent(gradovi);
		
		regije.addSelectionListener(e ->
			selectRegije(regije, zupanije, tipGrada, gradovi));
		
		gradovi.addSelectionListener(e -> {
			selectGrad(gradovi);
		});
		
		zupanije.addSelectionListener(e -> {
			selectZupanije(zupanije, tipGrada, gradovi);
		});
		
		tipGrada.addSelectionListener(e -> {
			selectZupanije(zupanije, tipGrada, gradovi);
		});
	}
	
	//Panel: Button
	public void panelButtons()
	{
		Panel panel = new Panel();
		HorizontalLayout layout = new HorizontalLayout();
		panel.setContent(layout);
		
		Button ponisti = new Button("Ponisti");
		ponisti.addClickListener(e -> {
			dq.ponisti();
			regije.setItems(this.oj.getRegije());
			zupanije.setItems(this.oj.getZupanije());
			tipGrada.setItems(this.velicinaGradaService.findAktivni());
			gradovi.setItems(this.gradService.findAll());
		});
		layout.addComponent(ponisti);
		
		Button unos = new Button("Unesi dogadjaj");
		unos.addClickListener(clickEvent ->
	    	this.getUI().getNavigator().navigateTo("unosDogadjaj"));
		layout.addComponent(unos);
		this.addComponent(panel);
	}
	
	//selektiranje regije
	public void selectRegije(CheckBoxGroup<Organizacijskajedinica> regije,
			CheckBoxGroup<Organizacijskajedinica> zupanije,
			CheckBoxGroup<Velicinagrada> tipGrada,
			ListSelect<Grad> gradovi)
	{
		List<Organizacijskajedinica> zupanijeList = null;
		Set<Organizacijskajedinica> selectedRegijeSet = regije.getSelectedItems();
		if(selectedRegijeSet == null || selectedRegijeSet.isEmpty())
			zupanijeList = this.oj.getZupanije();
		else
		{
			List<Organizacijskajedinica> selectedRegijeList = selectedRegijeSet.stream().collect(Collectors.toList());
			zupanijeList = this.oj.findByParentIn(selectedRegijeList);
		}
		Set<Organizacijskajedinica> selectedZupanijeSet = zupanije.getSelectedItems();
		zupanije.setItems(zupanijeList);
		zupanije.setValue(selectedZupanijeSet);
	}
	
	//selektiranje županije
	public void selectZupanije(CheckBoxGroup<Organizacijskajedinica> zupanije,
			CheckBoxGroup<Velicinagrada> tipGrada,
			ListSelect<Grad> gradovi)
	{
		Set<Organizacijskajedinica> selectedZupanijeSet = zupanije.getSelectedItems();
		Set<Velicinagrada> selectedVelicinaGradaSet = tipGrada.getSelectedItems();
		Set<Grad> selectedGradSet = gradovi.getSelectedItems();
		if((selectedZupanijeSet == null || selectedZupanijeSet.isEmpty()) && 
				(selectedVelicinaGradaSet == null || selectedVelicinaGradaSet.isEmpty()))
		{
			gradovi.setItems(this.gradService.findAll());
		}
		else if(selectedZupanijeSet == null || selectedZupanijeSet.isEmpty())
		{
			List<Velicinagrada> tipGradovaList = selectedVelicinaGradaSet.stream().collect(Collectors.toList());
			gradovi.setItems(this.gradService.findByVelicinaGradaIn(tipGradovaList));
		}
		else if(selectedVelicinaGradaSet == null || selectedVelicinaGradaSet.isEmpty())
		{
			List<Organizacijskajedinica> tipZupanijeList = selectedZupanijeSet.stream().collect(Collectors.toList());
			gradovi.setItems(this.gradService.findByOrganizacijskaJedinicaIn(tipZupanijeList));
		}
		else
		{
			List<Organizacijskajedinica> tipZupanijeList = selectedZupanijeSet.stream().collect(Collectors.toList());
			List<Velicinagrada> tipGradovaList = selectedVelicinaGradaSet.stream().collect(Collectors.toList());
			gradovi.setItems(this.gradService.findByOrganizacijskaJedinicaInAndVelicinaGradaIn(tipZupanijeList, tipGradovaList));
		}
		
		for(Grad grad : selectedGradSet)
		{
			gradovi.select(grad);
		}
	}
	
	//selektiranje tip grada
	public void selectTipGrada(CheckBoxGroup<Organizacijskajedinica> zupanije,
			CheckBoxGroup<Velicinagrada> tipGrada,
			ListSelect<Grad> gradovi)
	{
			
	}
	
	//selektiranje grada
	public void selectGrad(ListSelect<Grad> gradovi)
	{
		this.dq.setGradovi(gradovi.getSelectedItems().stream().collect(Collectors.toList()));
		this.dogadjajService.findByCriteria(this.dq);	
	}
	
	@Override
    public void enter(ViewChangeEvent event) {
		System.out.println("Pretrazi Događaj");
    }
}
