package ui;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

import service.DogadjajService;

@SpringUI
@Theme("valo")
public class StartVaadin extends UI {

	@Autowired
	DogadjajService dogadjajService;
	
	@Override
	protected void init(VaadinRequest request) {
		String notification = this.dogadjajService.findDogadjajById(1).getNaziv();
	    setContent(new Button("Click me", e -> Notification.show(notification)));
	}

}
