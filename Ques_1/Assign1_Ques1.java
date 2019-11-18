import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;

class Participant{
	private String name;
	private String address;
	private String gender;
	private String email;
	private String phoneNum;
	private int age;
	private int ug;
	Participant(String name, String address, String gender, String email, String phoneNum, int age, int ug){
		this.name = name;
		this.address = address;
		this.gender = gender;
		this.email = email;
		this.phoneNum = phoneNum;		
		this.age = age;
		this.ug = ug;
	}
	public String getGender(){
		return this.gender;
	}
	public int getUg(){
		return this.ug;
	}
	public int getAge(){
		return this.age;
	}

	public boolean checkNumRegs(int numReg){
		if(numReg == 0) {
			System.out.println("Participant should take part minimum in 1 event");
			return false;
		}
		else if(numReg > 3){
			System.out.println("Participant should take part maximum in 3 event");
			return false;
		}
		return true;
	}
	public boolean checkEventId(String[] ids, int numEvents){
		for(String t: ids){
			int temp = Integer.parseInt(t);
			if(temp < 0 || temp > numEvents){
				System.out.println("Select Event Ids between 0 and " + (numEvents - 1));
				return false;
			}
		}
		return true;
	}
}

class Event{
	//private Dictionary events;
	//events<int, String> = new Hashtable();
	private int eventId;
	private String eventName;
	private final int maxParticipants, duration, half;
	private boolean judgeNeeded = true;
	private static int maxMaleParticipants = 0, maxFemaleParticipants = 0,eventCount=0,totalParticipants = 200;
	private int regParticipants = 0;
	private ArrayList<Participant> participants = new ArrayList<Participant>();

	Event(String eventName,int maxParticipants,int duration,int half,boolean judgeNeeded){
		//this.events.put(eventCount,eventName);
		this.eventId = eventCount++;
		this.eventName = eventName;
		this.maxParticipants = maxParticipants;
		this.duration = duration;
		this.half = half;
		this.judgeNeeded = judgeNeeded;
	}
	public void display(){
		System.out.println(this.eventId+"  ---  "+this.eventName);
	}
	public int checkCriteria(String gender,int age,int ug,int topicsCount){

		if(gender.equals('m') || gender.equals('M')){
            if((this.maxMaleParticipants+1) > (int)(0.6*this.totalParticipants)){
                System.out.println("Male Registrations are closed!!!");
                return 0;
            }
        } else if(gender.equals('f') || gender.equals('F')){
            if((this.maxFemaleParticipants+1) > (int)(0.4*this.totalParticipants)){
                System.out.println("Female Registrations are closed!!!");
                return 0;
            }
        }
        if(regParticipants > maxParticipants) {
        	System.out.println("This Event is full-----Register for another one!!!");
        	return 0;
    	}
        regParticipants++;
        return 1;
	}
	public static int checkPorP(String[] eventIds){
		if(Arrays.asList(eventIds).contains("0") && Arrays.asList(eventIds).contains("1")){ 
			System.out.println(" Paper presentation and poster presentation cannot be opted together!!!!");
			return 1;
		}
		return 0;
	}
	public void addParticipant(Participant partObj, int eventId){
        this.participants.add(partObj);
        if(partObj.getGender().equals("M") || partObj.getGender().equals("m")){
            maxMaleParticipants++;
        } else{
            maxFemaleParticipants++;
        }
        System.out.println("Participant sucessfully registered for event with Id = " + eventId);
    }
}
class PosterPresentation extends Event{
    
    private final int maxTopics;
    PosterPresentation(int half, int maxTopics){
        super("PosterPresentation", 10, 15, half,true);
        this.maxTopics = maxTopics;
    }
    public int checkCriteria(String gender, int age, int batch, int topicsCount){
    	if(topicsCount > this.maxTopics || topicsCount <= 0){
    		System.out.println("One participant should not have more than two topic for Poster presentation");
    		return 0;
    	}
    	return super.checkCriteria(gender, age, batch, topicsCount);
    }
}
class PaperPresentation extends Event{

	private final int maxTopics;
	PaperPresentation(int half,int maxTopics){
		super("PaperPresentation",10,15,half,true);
		this.maxTopics = maxTopics;
	}
	public int checkCriteria(String gender,int age,int ug,int topicsCount){
		if(ug != 4){
			System.out.println("Only UG4 can take part in Paper Presentation");
			return 0;
		}
		if(topicsCount > this.maxTopics){
			System.out.println("One participant should not have more than two topic for Paper presentation");
			return 0;
		}
		return super.checkCriteria(gender,age,ug,topicsCount);
	}
}


class Quiz extends Event{
    
    private int maxMarks;
    Quiz(int maxParticipants){
        super("Quiz", maxParticipants, 30, 2, false);
        this.maxMarks = 30;
    }

}

class CulturalProgram extends Event{
    
    CulturalProgram(int Id, int maxPart, int timeDur){
        super("CulturalEvent"+Id, maxPart, timeDur, 3, true);
    }
}

class Registration{
	
	private static int numEvents = 3;
	public static ArrayList<Event> registerEvents(){
		Scanner s = new Scanner(System.in);
		
		ArrayList<Event> events = new ArrayList<Event>(8);

		events.add(new PosterPresentation(1, 2));
        events.add(new PaperPresentation(2, 2));
        events.add(new Quiz(20));

		System.out.print("Enter the number of cultural events you want (Maximum 5): ");
		int numCulturalEvents = Integer.parseInt(s.nextLine());
		if(numCulturalEvents > 5){
				System.out.println("You cannot Registrater more than 5 cultural events. Considering the first 5 events!!");
				numCulturalEvents = 5;
			}
		
		for(int i=1; i<=numCulturalEvents; i++){
			System.out.println("-------------------------------------------------------------------------");
			
			System.out.print("Enter the maximum participation: ");int maxP = Integer.parseInt(s.nextLine());
			System.out.print("Enter the time duration of the event: ");int maxT = Integer.parseInt(s.nextLine());
			while(maxT < 15){
				System.out.print("Minimum duration of 1 program is 15 min. Re-enter the Minimum duration (>15min): ");
				maxT = Integer.parseInt(s.nextLine());
			}
			events.add(new CulturalProgram(i, maxP, maxT));
			numEvents++;
		}
		return events;
	}

	public static Participant registerParticipant(){
		Scanner s = new Scanner(System.in);

		System.out.print("Enter your Name: ");String name = s.nextLine();
            System.out.print("Enter your Address: ");String address = s.nextLine();
            System.out.print("Enter your Gender (M/F): ");String gender = s.nextLine();
            while(!gender.equals("M") && !gender.equals("m") && !gender.equals("F") && !gender.equals("f")){
            	System.out.print("Re-enter your age properly (M/F): ");gender = s.nextLine();
            }
            System.out.print("Enter your Email: ");String email = s.nextLine();
            String regex = "^(.+)@(.+)$";
      		Pattern pattern = Pattern.compile(regex);
      		while(!pattern.matcher((CharSequence) email).matches()){
      			System.out.print("Re-enter your Email Id properly: ");email = s.nextLine();
      		}
            
            System.out.print("Enter your Mobile Number: ");String moblie_no = s.nextLine();
            
            while(!moblie_no.matches("^(?=(?:[6-9]){1})(?=[0-9]{8}).*")){
      			System.out.print("Re-enter your Phone Number properly: ");moblie_no = s.nextLine();
      		}
            int batch, age;
            while(true){
            	System.out.print("Enter your Batch (1,2,3,4): ");batch = Integer.parseInt(s.nextLine());
	            if(batch > 4 || batch <= 0){
	            	System.out.println("There are only 4 batches (1,2,3,4)");
	            	continue;
	            }
    	        break;
            }
	        System.out.print("Enter your Age: ");age = Integer.parseInt(s.nextLine());
            Participant participant = new Participant(name, address, gender, email, moblie_no, age, batch);
            return participant;
	}

	public static void registerForEvents(Participant participant, ArrayList<Event> eventList){
		Scanner s = new Scanner(System.in);

		System.out.print("Enter the Event Ids for which you want to register (Space seperated): ");
        String events = s.nextLine();
        String[] eventIds = events.split(" ");
        while(!participant.checkNumRegs(eventIds.length) || events.equals("")|| !participant.checkEventId(eventIds, numEvents) ||(Arrays.asList(eventIds).contains("1") && eventList.get(1).checkCriteria(participant.getGender(), participant.getAge(), participant.getUg(), 2) == 0 )|| Event.checkPorP(eventIds) == 1){
        	System.out.print("Re-enter the Event Ids: ");
			events = s.nextLine();
			eventIds = events.split(" ");
        }
        Registration.registerStudent(eventIds, s, participant, eventList);
	}

	public static void registerStudent(String[] eventIds, Scanner scan, Participant participant, ArrayList<Event> eventList){
		for(String t: eventIds){
            int var = Integer.parseInt(t);
            int numT;
            	if(var == 1){
            	System.out.print("Enter Number of topics for Paper Presentation: ");
            	numT = Integer.parseInt(scan.nextLine());
				while(numT == 0 || eventList.get(var).checkCriteria(participant.getGender(), participant.getAge(), participant.getUg(), numT) == 0){
            		System.out.print("Re-Enter the number of topics: ");
					numT = Integer.parseInt(scan.nextLine());
            	}
            	eventList.get(var).addParticipant(participant, var);
			} else if(var == 0){
            System.out.print("Enter Number of topics for Poster Presentaion: ");
            	numT = Integer.parseInt(scan.nextLine());
            	while(numT == 0 || eventList.get(var).checkCriteria(participant.getGender(), participant.getAge(), participant.getUg(), numT) == 0){
            		System.out.print("Re-Enter the number of topics: ");
	                numT = Integer.parseInt(scan.nextLine());
            	}
            	eventList.get(var).addParticipant(participant, var);
            } else if(var == 2 && eventList.get(var).checkCriteria(participant.getGender(), participant.getAge(), participant.getUg(), 0) == 1){
				eventList.get(var).addParticipant(participant, var);
            } else{
				eventList.get(var).addParticipant(participant, var);
			}
        }
	}
}

class Assign1_Ques1{

	public static void main(String[] args){
		Scanner s = new Scanner(System.in);
		
		ArrayList<Event> events = Registration.registerEvents();
		System.out.println("################################################################");
		for (int i=0; i<events.size(); i++) 
            events.get(i).display(); 
		System.out.println("################################################################");
		System.out.print("Enter number of participants who wanted to Register: ");
		int numP = Integer.parseInt(s.nextLine());
		for(int i=0; i<numP; i++){
			System.out.println("################################################################");
			Participant participant = Registration.registerParticipant();
			if(participant.getAge() < 18){
            	System.out.println("Participant should be above 18 yrs of Age!!! You Cannot Register for the event!!!");
            	i--;
            	continue;
            }
            Registration.registerForEvents(participant, events);
        }
	}
}