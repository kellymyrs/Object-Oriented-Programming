package Airplane;
import java.util.*;

abstract class PlaneComponent{
    protected String description;
    
    //constructor
    PlaneComponent(String des){ 
        description=des;
    }
    
    //sunarthseis
    abstract void ready_check();
    abstract void process(Employee x);
    public String get_description(){
        return description;
    }
}


class PassengerCompartment extends PlaneComponent{
    private PassengerCompartment in;
    
    //constructor
    PassengerCompartment(String des){
        super(des);
        Random rand = new Random();
        if(rand.nextInt(2)==0)
            in=null;
        else
            in= new PassengerCompartment(des);
        System.out.println("A passenger compartment is constructed");
    }
    
    //sunarthseis
    public void ready_check(){
        System.out.println("Passenger Compartment OK!");
        if(in!=null){
            System.out.println("Inner passenger compartment OK!");
        }
    }
    public void process(Employee x){
        x.workOn(description);
        if(in!=null){
            x.workOn(in.description);
        }
    }
}


abstract class PrivateCompartment extends PlaneComponent{
    //constructor
    PrivateCompartment(String des){
        super(des);
    }
    //sunarthseis
    abstract public void ready_check();
    abstract public void process(Employee x);
}



class EquipmentCompartment extends PrivateCompartment{
    //constructor
    EquipmentCompartment(String des){
        super(des);
        System.out.println("An equipment compartment is constructed");
    }
    //sunarthseis
    public void ready_check(){
         System.out.println("Equipment Compartment OK!");
    }
    public void process(Employee x){
        x.workOn(description);
    }
}

class CargoBay extends PrivateCompartment{
    private EquipmentCompartment ec;
    
    //constructor
    CargoBay(String des1,String des2){
        super(des1);
        ec=new EquipmentCompartment(des2);
        System.out.println("A Cargo Bay is constructed");
    }
    
    //sunarthseis
    public void ready_check(){
        System.out.println("Cargobay :");
        ec.ready_check();
        System.out.println("CargoBay OK!");
    }
    public void process(Employee x){
        if(x instanceof MaintenanceEmployee || x instanceof SecurityEmployee)
            ec.process(x);
        x.workOn(description);
    }
}

abstract class Employee {
    private String name;
    
    //constructor
    Employee(String nam){
        name=nam;
    }
    
    //sunarthseis
    public abstract void workOn(String des);
    public abstract void report();
}

class SecurityEmployee extends Employee{
    //constructor
    SecurityEmployee(String nam){
        super(nam);
        System.out.println("A security employee is ready for work");
    }
    
    //sunarthseis
    public void workOn(String des){
        System.out.println("Security employee is working at " + des );
    }
    public void report(){
        System.out.println("Securty OK!");
    }
}

class MaintenanceEmployee extends Employee{
    //constructor
    MaintenanceEmployee(String nam){
        super(nam);
        System.out.println("A maintenance employee is ready for work");
    }
    
    //sunarthseis
    public void workOn(String des){
        System.out.println("Maintenance employee is working at " + des );
    }
    public void report(){
        System.out.println("Maintenance OK!");
    }
}

class CleaningEmployee extends Employee{
    //constructor
    CleaningEmployee(String nam){
        super(nam);
        System.out.println("A cleaning employee is ready for work");
    }
    
    //sunarthseis
    public void workOn(String des){
        System.out.println("Cleaning employee is working at " + des);
    }
    public void report(){
        System.out.println("Cleaning OK!");
    }
}

class Plane{
    private final String title;
    private final String description;
    private final int capacity;
    private final EquipmentCompartment[] eq;
    private final CargoBay cargobay;
    private final PassengerCompartment[] pascomp;
    private final int c; //plithos PassengerCompartments
    
    //conastructor
    Plane(String t,String des){
        int i,size;
        title=t;
        description=des;
        Random rand = new Random(); 
        capacity = rand.nextInt(150)+50;
        cargobay=new CargoBay("cargobay","equipmentcompartment");
        eq=new EquipmentCompartment[3];
        for(i=0;i<=2;i++){
            eq[i]=new EquipmentCompartment("equipmentcompartment");
        }
        size=rand.nextInt(50)+1;
        if(capacity%size==0)
            c=capacity/size;
        else
            c=capacity/size+1;
        
        pascomp=new PassengerCompartment[c];
        for(i=0;i<=c-1;i++){
            pascomp[i]=new PassengerCompartment("passengercompartment");
        } 
        System.out.println("Plane constructed");
    }
    
    //sunarthseis
    public void ready_check(){
        int i;
        cargobay.ready_check();
        for(i=0;i<=2;i++){
            System.out.println("Equipment compartment:" + eq[i].get_description() + (i+1));
            eq[i].ready_check();
        }
        for(i=0;i<=c-1;i++){
            System.out.println("Passenger compartment:" + pascomp[i].get_description() + (i+1));
            pascomp[i].ready_check();
        }
    }
    public void process(SecurityEmployee x){
        int i;
        cargobay.process(x);
        for(i=0;i<=2;i++){
            eq[i].process(x);
        }
        for(i=0;i<=c-1;i++){
            pascomp[i].process(x);
        }
        x.report();
    }
    public void process(CleaningEmployee x){
        int i;
        for(i=0;i<=2;i++){
            eq[i].process(x);
        }
        for(i=0;i<=c-1;i++){
            pascomp[i].process(x);
        }
        x.report();
    }
    public void process(MaintenanceEmployee x){
        int i;
        cargobay.process(x);
        for(i=0;i<=2;i++){
            eq[i].process(x);
        }
        x.report();
    }
    
    //getter sunarthseis
    public String get_title(){
        return title;
    }
    public String get_description(){
        return description;
    }
    public int get_capacity(){
        return capacity;
    }
    public int get_num_of_pascomp(){
        return c;
    }
    public CargoBay get_cargo_bay(){
        return cargobay;
    }
    public EquipmentCompartment[] get_equipment_compartment(){
        return eq;
    }
    public PassengerCompartment[] get_passenger_compartment(){
        return pascomp;
    }
}


public class Airplane {

    public static void main(String[] args) {
        Plane airplane=new Plane("RyanAir","314");
        SecurityEmployee secem=new SecurityEmployee("Security");
        CleaningEmployee cleanem=new CleaningEmployee("Cleaner");
        MaintenanceEmployee mainem=new MaintenanceEmployee("Maintenance");
        airplane.process(secem);
        airplane.process(cleanem);
        airplane.process(mainem);
        airplane.ready_check();
        System.out.println("Plane " + airplane.get_title() + airplane.get_description() + " is ready to take off!");
    }
    
}
