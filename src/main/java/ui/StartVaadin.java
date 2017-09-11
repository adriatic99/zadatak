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
	    navigator.addProvider(viewProvider);
	    navigator.navigateTo("searchDogadjaj");
	    
	    VerticalLayout verticalMain = new VerticalLayout();
	    
	    HorizontalLayout vertical = new HorizontalLayout();
	    Panel panel = new Panel("Filter gradova");
	    panel.setContent(vertical);
	    
	    dogadjaji = this.dogadjajService.findByCriteria(dq);
	    
	    Grid<Dogadjaj> grid = new Grid<>(Dogadjaj.class);
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
	    verticalMain.addComponent(grid);
	    
	    Panel panelData = new Panel();
	    verticalMain.addComponent(panelData);
	    
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

            Set<Organizacijskajedinica> selectedR = e.getAllSelectedItems();
    	    List<Organizacijskajedinica> selectedRegijeList = selectedR.stream().collect(Collectors.toList());
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
	    
	    verticalMain.addComponent(panel);
	    this.setContent(verticalMain);
	}

}
