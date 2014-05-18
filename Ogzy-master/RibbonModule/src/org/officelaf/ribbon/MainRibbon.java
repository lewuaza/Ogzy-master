package org.officelaf.ribbon;

import java.awt.*;
import java.util.Arrays;
import org.gui.mailer.Settings;
import org.jvnet.flamingo.common.AbstractCommandButton;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.icon.EmptyResizableIcon;
import org.jvnet.flamingo.common.icon.IcoWrapperResizableIcon;
import org.jvnet.flamingo.ribbon.*;
import org.jvnet.flamingo.ribbon.resize.CoreRibbonResizePolicies.Mid2Low;
import org.jvnet.flamingo.ribbon.resize.CoreRibbonResizePolicies.Mid2Mid;
import org.jvnet.flamingo.ribbon.resize.RibbonBandResizePolicy;
import org.jvnet.flamingo.ribbon.ui.JBandControlPanel;
import org.officelaf.OfficeRibbonApplicationMenuButtonUI;
import org.officelaf.OfficeRootPaneUI;
import org.officelaf.listeners.TopComponentsManagerListener;
import org.officelaf.ribbon.grupy_cwiczeniowe.DodajGrupeAction;
import org.officelaf.ribbon.grupy_cwiczeniowe.PokazGrupyAction;
import org.officelaf.ribbon.grupy_cwiczeniowe.UsunGrupeAction;
import org.officelaf.ribbon.mailer.InboxAction;
import org.officelaf.ribbon.mailer.InboxListAction;
import org.officelaf.ribbon.mailer.NewMessageAction;
import org.officelaf.ribbon.mailer.SettingsAction;
import org.officelaf.ribbon.menu.PDFAction;
import org.officelaf.ribbon.obecnosc.SprawdzObecnoscAction;
import org.officelaf.ribbon.obecnosc.UsunObecnoscAction;
import org.officelaf.ribbon.oceny.DodajSlupekAction;
import org.officelaf.ribbon.oceny.PodsumowanieAction;
import org.officelaf.ribbon.oceny.PokazTabeleOcenAction;
import org.officelaf.ribbon.oceny.UsunSlupekAction;
import org.officelaf.ribbon.plan.DodajTerminAction;
import org.officelaf.ribbon.plan.PokazPlanAction;
import org.officelaf.ribbon.plan.UsunTerminAction;
import org.officelaf.ribbon.przedmioty.DodajPrzedmiotAction;
import org.officelaf.ribbon.przedmioty.PokazPrzedmiotyAction;
import org.officelaf.ribbon.przedmioty.UsunPrzedmiotAction;
import org.officelaf.ribbon.schematy.DodajSchematAction;
import org.officelaf.ribbon.schematy.PokazSchematyAction;
import org.officelaf.ribbon.schematy.UsunSchematAction;
import org.officelaf.ribbon.studenci.DodajStudentaAction;
import org.officelaf.ribbon.studenci.OdepnijStudentaAction;
import org.officelaf.ribbon.studenci.PodepnijStudentaAction;
import org.officelaf.ribbon.studenci.PokazListeAction;
import org.officelaf.ribbon.studenci.UsunStudentaAction;
import org.openide.util.NbBundle;
import org.openide.windows.WindowManager;

public class MainRibbon extends JRibbon {
    
    /**
     * Icons:
     */
    private IcoWrapperResizableIcon table_ico = null;
    private IcoWrapperResizableIcon table_plus_ico = null;
    private IcoWrapperResizableIcon table_minus_ico = null;
    private IcoWrapperResizableIcon percent_ico = null;
    private IcoWrapperResizableIcon percent_plus_ico = null;
    private IcoWrapperResizableIcon percent_minus_ico = null;
    private IcoWrapperResizableIcon subject_ico = null;
    private IcoWrapperResizableIcon subject_plus_ico = null;
    private IcoWrapperResizableIcon subject_minus_ico = null;
    private IcoWrapperResizableIcon group_ico = null;
    private IcoWrapperResizableIcon group_plus_ico = null;
    private IcoWrapperResizableIcon group_minus_ico = null;
    private IcoWrapperResizableIcon student_ico = null;
    private IcoWrapperResizableIcon student_plus_ico = null;
    private IcoWrapperResizableIcon student_minus_ico = null;
    private IcoWrapperResizableIcon student_add_ico = null;
    private IcoWrapperResizableIcon student_delete_ico = null;
    private IcoWrapperResizableIcon notes_ico = null;
    private IcoWrapperResizableIcon notes_plus_ico = null;
    private IcoWrapperResizableIcon notes_minus_ico = null;
    private IcoWrapperResizableIcon notes_last_ico = null;
    private IcoWrapperResizableIcon presence_table_ico = null;
    private IcoWrapperResizableIcon presence_minus_ico = null;
    private IcoWrapperResizableIcon pdf_ico = null;
    
    public MainRibbon() {

    }
    
    public static AbstractCommandButton getRibbonButton(int task_position, int band_position,
            int group_position, RibbonElementPriority ribbonElementPriority, int button_position){

        JRibbon ribbon = OfficeRootPaneUI.getRibbon();
        RibbonTask task = ribbon.getTask(task_position);
        if(task == null){
            throw new NullPointerException("Task not exist! :"+uiClassID);
        }
        JRibbonBand band = (JRibbonBand) task.getBand(band_position);
        if(band == null){
            throw new NullPointerException("Band not exist! :"+uiClassID);
        }
        JBandControlPanel band_control_panel = band.getControlPanel();
        java.util.List<JBandControlPanel.ControlPanelGroup> groups_list = band_control_panel.getControlPanelGroups();
        JBandControlPanel.ControlPanelGroup group = groups_list.get(group_position);
        
        if(group == null){
            throw new NullPointerException("Group not exist! :"+uiClassID);
        }
        
        java.util.List<AbstractCommandButton> buttons_list = group.getRibbonButtons(ribbonElementPriority);
        
        AbstractCommandButton button = buttons_list.get(button_position);
        
        if(button == null){
            throw new NullPointerException("Button not exist! :"+uiClassID);
        }
        
        return button;
        
    }
    
    private void initIcons(){
        
        table_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/table.ico"), new Dimension(32, 32));
        table_plus_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/table_plus.ico"), new Dimension(32, 32));
        table_minus_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/table_minus.ico"), new Dimension(32, 32));
        percent_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/percent.ico"), new Dimension(32, 32));
        percent_plus_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/percent_plus.ico"), new Dimension(32, 32));
        percent_minus_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/percent_minus.ico"), new Dimension(32, 32));
        subject_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/subject.ico"), new Dimension(32, 32));
        subject_plus_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/subject_plus.ico"), new Dimension(32, 32));
        subject_minus_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/subject_minus.ico"), new Dimension(32, 32));
        group_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/group.ico"), new Dimension(32, 32));
        group_plus_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/group_plus.ico"), new Dimension(32, 32));
        group_minus_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/group_minus.ico"), new Dimension(32, 32));
        student_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/student.ico"), new Dimension(32, 32));
        student_plus_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/student_plus.ico"), new Dimension(32, 32));
        student_minus_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/student_minus.ico"), new Dimension(32, 32));
        student_add_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/student_add.ico"), new Dimension(32, 32));
        student_delete_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/student_delete.ico"), new Dimension(32, 32));
        notes_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/notes.ico"), new Dimension(32, 32));
        notes_plus_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/notes_plus.ico"), new Dimension(32, 32));
        notes_minus_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/notes_minus.ico"), new Dimension(32, 32));
        notes_last_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/notes_last.ico"), new Dimension(32, 32));
        presence_table_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/presence.ico"), new Dimension(32, 32));
        presence_minus_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/presence_minus.ico"), new Dimension(32, 32));
        pdf_ico = IcoWrapperResizableIcon.getIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("icons/images/pdf.ico"), new Dimension(32, 32));
    }
    
    public void setup() {
        
        initIcons();
        
        RibbonTask homeTask = new RibbonTask("Narzędzia głowne", createHomeBands());
        RibbonTask subTask = new RibbonTask("Zarządzanie przedmiotami", createSubBands());
        RibbonTask peopleTask = new RibbonTask("Zarządzanie grupami", createPeopleBands());
        RibbonTask mailTask = new RibbonTask("Skrzynka pocztowa", this.createMailerSettingsBand());
        //RibbonTask mailTask = new RibbonTask("Skrzynka pocztowa", createPeopleBands());
        
        addTask(homeTask);
        addTask(peopleTask);
        addTask(subTask);
        addTask(mailTask);
        
        createApplicationMenu();
        
        WindowManager.getDefault().getRegistry().addPropertyChangeListener(new TopComponentsManagerListener());
        
    }

    private void createApplicationMenu() {
        setApplicationMenu(new RibbonApplicationMenu());
    }
    
    private AbstractRibbonBand[] createSubBands(){
        AbstractRibbonBand[] bands = new AbstractRibbonBand[2];
        bands[0] = createSubjectsBand();
        bands[1] = createSchemeBand();
        
        return bands;
    }
    
    private AbstractRibbonBand[] createPeopleBands(){
        AbstractRibbonBand[] bands = new AbstractRibbonBand[2];
        bands[0] = createGroupsBand();//Opcje grup zajęciowych
        bands[1] = createStudentsBand();        
        return bands;
    }
    private AbstractRibbonBand[] createMailBands(){
        AbstractRibbonBand[] bands = new AbstractRibbonBand[1];
        bands[0] = createMailerSettingsBand();
        return bands;
    }
    private JRibbonBand createMailerSettingsBand() {
        JRibbonBand band = new JRibbonBand(NbBundle.getMessage(MainRibbon.class, "MAIL_BAND"),
                new EmptyResizableIcon(16));
        
        band.addCommandButton(
                new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Dodaj nową skrzynkę", "Skrzynka mailowa", student_ico, null, new SettingsAction()),
                RibbonElementPriority.TOP);
        
        band.addCommandButton(new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Lista skrzynek pocztowych", "Skrzynka mailowa", student_ico, null, new InboxListAction()),
                RibbonElementPriority.TOP);
        
        band.addCommandButton(
                new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Skrzynka pocztowa", "Skrzynka pocztowa", student_ico, null, new InboxAction()),
                RibbonElementPriority.TOP);
        
        band.addCommandButton(
                new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Nowa wiadomość", "Nowa wiadomość", student_ico, null, new NewMessageAction()),
                RibbonElementPriority.TOP);
        
        band.setResizePolicies(Arrays.<RibbonBandResizePolicy>asList(
                new Mid2Mid(band.getControlPanel()),
                new Mid2Mid(band.getControlPanel()),
                new Mid2Mid(band.getControlPanel())
        ));
        return band;
    }
    
    private AbstractRibbonBand[] createHomeBands() {

        AbstractRibbonBand[] bands = new AbstractRibbonBand[4];
        bands[0] = createPlansBand(); //Opcje planu zajec
        bands[1] = createNotesBand();
        bands[2] = createPresenceBand();
        bands[3] = createExportBand();
        return bands;
    }

    private JRibbonBand createPlansBand() {
        JRibbonBand band = new JRibbonBand(NbBundle.getMessage(MainRibbon.class, "PLAN_BAND"),
                new EmptyResizableIcon(16));
        
        AbstractCommandButton showButton = new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Pokaż plan", "Ukazuje plan zajęć w tabeli", table_ico, null, new PokazPlanAction());
        AbstractCommandButton addButton = new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Dodaj termin", "Dodaje termin do planu zajęć", table_plus_ico, null, new DodajTerminAction());
        AbstractCommandButton removeButton = new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Usuń termin", "Usuwa termin z planu zajęć", table_minus_ico, null, new UsunTerminAction());
        
        addButton.setEnabled(false);
        removeButton.setEnabled(false);
        
        band.addCommandButton(showButton, RibbonElementPriority.TOP);
        band.addCommandButton(addButton, RibbonElementPriority.TOP);
        band.addCommandButton(removeButton, RibbonElementPriority.TOP);

        band.setResizePolicies(Arrays.<RibbonBandResizePolicy>asList(
                new Mid2Mid(band.getControlPanel()),
                new Mid2Mid(band.getControlPanel()),
                new Mid2Low(band.getControlPanel())
        ));
        return band;
    }
    
    private JRibbonBand createSchemeBand(){
        JRibbonBand band = new JRibbonBand(NbBundle.getMessage(MainRibbon.class, "SCHEME_BAND"),
                new EmptyResizableIcon(16));
        
        band.addCommandButton(
                new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Pokaż schematy", "Ukazuje schematy w liście", percent_ico, null, new PokazSchematyAction()),
                RibbonElementPriority.TOP);
        band.addCommandButton(
                new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Dodaj schemat", "Dodaje schemat do bazy", percent_plus_ico, null, new DodajSchematAction()),
                RibbonElementPriority.TOP);
        band.addCommandButton(
                new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Usuń schemat", "Usuwa schemat z bazy", percent_minus_ico, null, new UsunSchematAction()),
                RibbonElementPriority.TOP);
        
        band.setResizePolicies(Arrays.<RibbonBandResizePolicy>asList(
                new Mid2Mid(band.getControlPanel()),
                new Mid2Mid(band.getControlPanel()),
                new Mid2Low(band.getControlPanel())
        ));
        
        return band;
    }
    
    private JRibbonBand createSubjectsBand() {
        JRibbonBand band = new JRibbonBand(NbBundle.getMessage(MainRibbon.class, "SUBJECT_BAND"),
                new EmptyResizableIcon(16));

        band.addCommandButton(
                new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Pokaż przedmioty", "Ukazuje przedmioty w tabeli", subject_ico, null, new PokazPrzedmiotyAction()),
                RibbonElementPriority.TOP);
        band.addCommandButton(
                new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Dodaj przedmiot", "Dodaje przedmioty do bazy", subject_plus_ico, null, new DodajPrzedmiotAction()),
                RibbonElementPriority.TOP);
        band.addCommandButton(
                new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Usuń przedmiot", "Usuwa przedmioty z bazy", subject_minus_ico, null, new UsunPrzedmiotAction()),
                RibbonElementPriority.TOP);

        band.setResizePolicies(Arrays.<RibbonBandResizePolicy>asList(
                new Mid2Mid(band.getControlPanel()),
                new Mid2Mid(band.getControlPanel()),
                new Mid2Low(band.getControlPanel())
        ));
        return band;
    }
    
    private JRibbonBand createGroupsBand() {
        JRibbonBand band = new JRibbonBand(NbBundle.getMessage(MainRibbon.class, "GROUP_BAND"),
                new EmptyResizableIcon(16));

        band.addCommandButton(
                new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Pokaż grupy", "Ukazuje grupę w nowym oknie", group_ico, null, new PokazGrupyAction()),
                RibbonElementPriority.TOP);
        band.addCommandButton(
                new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Dodaj grupę", "Dodaję grupę do systemu", group_plus_ico, null, new DodajGrupeAction()),
                RibbonElementPriority.TOP);
        band.addCommandButton(
                new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Usuń grupę", "Usuwa grupę z systemu", group_minus_ico, null, new UsunGrupeAction()),
                RibbonElementPriority.TOP);

        band.setResizePolicies(Arrays.<RibbonBandResizePolicy>asList(
                new Mid2Mid(band.getControlPanel()),
                new Mid2Mid(band.getControlPanel()),
                new Mid2Low(band.getControlPanel())
        ));
        return band;
    }

    
    
    private JRibbonBand createStudentsBand() {
        JRibbonBand band = new JRibbonBand(NbBundle.getMessage(MainRibbon.class, "STUDENTS_BAND"),
                new EmptyResizableIcon(16));

        band.addCommandButton(
                new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Pokaż listę", "Ukazuje wszystkich studentów w nowym oknie", student_ico, null, new PokazListeAction()),
                RibbonElementPriority.TOP);
        band.addCommandButton(
                new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Dodaj studenta", "Dodaję studenta do systemu", student_plus_ico, null, new DodajStudentaAction()),
                RibbonElementPriority.TOP);
        band.addCommandButton(
                new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Usuń studenta", "Usuwa studenta z systemu", student_minus_ico, null, new UsunStudentaAction()),
                RibbonElementPriority.TOP);
        band.addCommandButton(
                new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Podepnij studenta", "Podpina studenta do grupy ćwiczeniowej", student_add_ico, null, new PodepnijStudentaAction()),
                RibbonElementPriority.TOP);
        band.addCommandButton(
                new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Odepnij studenta", "Odpina studenta z grupy ćwiczeniowej", student_delete_ico, null, new OdepnijStudentaAction()),
                RibbonElementPriority.TOP);

        band.setResizePolicies(Arrays.<RibbonBandResizePolicy>asList(
                new Mid2Mid(band.getControlPanel()),
                new Mid2Mid(band.getControlPanel()),
                new Mid2Mid(band.getControlPanel()),
                new Mid2Mid(band.getControlPanel())
        ));
        return band;
    }

    private JRibbonBand createNotesBand() {
        JRibbonBand band = new JRibbonBand(NbBundle.getMessage(MainRibbon.class, "NOTES_BAND"),
                new EmptyResizableIcon(16));
        
        AbstractCommandButton showButton = new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Pokaż tabele", "Ukazuje tabele wszystkich ocen w grupie", notes_ico, null, new PokazTabeleOcenAction());
        AbstractCommandButton addButton = new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Dodaj słupek", "Dodaje słupek ocen do kategorii w grupie ocen", notes_plus_ico, null, new DodajSlupekAction());
        AbstractCommandButton removeButton = new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Usuń słupek", "Usuwa słupek ocen z kategorii w grupie ćwiczeniowej", notes_minus_ico, null,new UsunSlupekAction());
        AbstractCommandButton sumButton = new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Podsumuj", "Podsumowuje i wylicza oceny końcowe", notes_last_ico, null, new PodsumowanieAction());
        
        showButton.setEnabled(false);
        addButton.setEnabled(false);
        removeButton.setEnabled(false);
        sumButton.setEnabled(false);
        
        band.addCommandButton(showButton, RibbonElementPriority.TOP);
        band.addCommandButton(addButton, RibbonElementPriority.TOP);
        band.addCommandButton(removeButton, RibbonElementPriority.TOP);
        band.addCommandButton(sumButton, RibbonElementPriority.TOP);

        band.setResizePolicies(Arrays.<RibbonBandResizePolicy>asList(
                new Mid2Mid(band.getControlPanel()),
                new Mid2Mid(band.getControlPanel()),
                new Mid2Low(band.getControlPanel())
        ));
        return band;
    }

    private JRibbonBand createPresenceBand() {
        JRibbonBand band = new JRibbonBand(NbBundle.getMessage(MainRibbon.class, "PRESENCE_BAND"),
                new EmptyResizableIcon(16));
        
        AbstractCommandButton showButton = new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Sprawdź ocebność", "Dodaje słupek obecności z dzisiejszą datą", presence_table_ico, null, new SprawdzObecnoscAction());
        AbstractCommandButton removeButton = new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Usuń ocebność", "Usuwa zaznaczony słupek obecności", presence_minus_ico, null, new UsunObecnoscAction());
        
        showButton.setEnabled(false);
        removeButton.setEnabled(false);
        
        band.addCommandButton(showButton, RibbonElementPriority.TOP);
        band.addCommandButton(removeButton, RibbonElementPriority.TOP);

        band.setResizePolicies(Arrays.<RibbonBandResizePolicy>asList(
                new Mid2Mid(band.getControlPanel()),
                new Mid2Mid(band.getControlPanel()),
                new Mid2Low(band.getControlPanel())
        ));
        return band;
    }
    private JRibbonBand createExportBand() {
        JRibbonBand band = new JRibbonBand(NbBundle.getMessage(MainRibbon.class, "EXPORT_BAND"),
                new EmptyResizableIcon(16));
        
        band.addCommandButton(
                new BoundCommandButton(JCommandButton.CommandButtonKind.ACTION_ONLY, "Eksport do PDF", "Eksportuje widok do PDF", pdf_ico, null, new PDFAction()),
                RibbonElementPriority.TOP);

        band.setResizePolicies(Arrays.<RibbonBandResizePolicy>asList(
                new Mid2Mid(band.getControlPanel()),
                new Mid2Mid(band.getControlPanel()),
                new Mid2Low(band.getControlPanel())
        ));
        return band;
    }
}
