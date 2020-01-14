/* Dieser Code ist sehr spaghettihaft.
 * Das liegt eventuell daran, dass ich ihn zum Großteil während der Uni hingeklatscht habe und
 * später keine Lust mehr hatte, den noch groß aufzuräumen.
 * Versucht einfach, nicht zu weit runterzuscrollen.
 */

import java.util.ArrayList;

public class Main {
	
    public static void main(String args[]) {
    	// Dieser Teil sollte nicht geändert werden! 
    	long time = System.currentTimeMillis();
    	Job job1 = new Job("Zeitungsjunge/-mädchen",-211,-478,26,66,15*60);
    	Job job3 = new Job("Lieferant*in",-391,62,52,100,29*60); // Daten sind Durchschnitt zwischen Fischer und Supermarkt
    	job3.setEnd(-39,280); // Liegt genau zwischen Fischer und Supermarkt
        Job job4 = new Job("Hochseefischer*in",-428,142,79,197,27*60);
        Job job5 = new Job("Urantransport",-500,687,56,282,15*60);
        job5.setEnd(454,61);
        Job job6 = new Job("Bergarbeiter*in",-247,555,15,71,9*60);
        Job job7 = new Job("Getränkelieferant*in",17,558,12,97,13*60);
        job7.setEnd(4,192);
        Job job8 = new Job("Winzer*in",17,549,31,167,20*60);
        Job job10 = new Job("Tabakplantage",379,477,7,510,25*60);
        job10.setEnd(-160,-31);
        Job job12 = new Job("Pulvermine",510,192,19,166,15*60);
        Job job13 = new Job("Müllhalde",846,367,49,65,24*60);
        // Der obenstehende Teil sollte nicht geändert werden!
        
        
        
        
        /* Mit r.add(jobX); wird ein Job in dieser Reihenfolge zur Route hinzugefügt
         * Wofür jobX steht, ist im obenstehenden Teil sichtbar (job12 steht zum Beispiel für die Pulvermine)
         * Dieser Teil ist nur relevant, wenn eine spezifische Jobroute auf ihre Effizienz getestet werden soll.
         * Soll dies getan werden, so muss das // vor r.generateProtocol entfernt werden.
         * Soll die beste Jobroute berechnet werden, so kann dieser Teil ignoriert werden
         * Die erste Zahl in den Klammern gibt an, wie viele Sekunden man maximal arbeiten möchte, die zweite und dritte Zahl die
         * x- und z-Koordinaten, bei denen man sich aktuell befindet.
         */
        Route r = new Route();
        r.add(job13);
        r.add(job1);
        r.add(job3);
        r.add(job8);
        r.add(job7);
        r.add(job6);
        r.add(job5);
        r.add(job12);
        // r.generateProtocol(36000, 126, 227);
        
        
        
        
        /* Der untenstehende Teil ist nur relevant, wenn die beste Jobroute berechnet werden soll.
         * Soll dies getan werden, so ist das // vor findOptimalRoutes zu entfernen.
         * Soll lediglich eine spezifische Jobroute auf ihre Effizienz getestet werden, so kann
         * diese Zeile ignoriert werden.
         */
        
        Job[] jobArray = new Job[] {job1,job3,job4,job5,job6,job7,job8,job10,job12,job13}; // soll ein bestimmter Job nicht in die Jobroute einfließen, kann dieser Job hier rausgenommen werden
        findOptimalRoutes(jobArray, 3600, 126, 227); // die erste Zahl gibt die maximale Arbeitsdauer in Sekunden an, die zweite und dritte die x- und z-Koordinaten, bei denen man sich aktuell befindet
        
        
        
        
        // Alles ab hier sollte nicht geändert werden
        System.out.println("Durchlauf beendet nach "+(System.currentTimeMillis()-time)+"ms.");
    }
	
	
	
    
   /* ALLES AB HIER IST EXTREM SCHLECHT LESBAR!
    * TUT EUCH SELBER EINEN GEFALLEN UND LEST HIER NICHT MEHR WEITER!    
    */
    
    
    
	
    private static class Job {
        public static boolean isEqual(Job job1, Job job2) {
            if(job1.getX()==job2.getX()&&job1.getZ()==job2.getZ()) return true;
            else return false;
        }
        double x,z,salary,endX,endZ;
        int time,block;
        String name;
        public Job(String name,double x, double z, double salary, int time, int block) {
            this.x = x;
            this.z = z;
            this.salary = salary;
            this.time = time;
            this.block = block;
            this.endX = x;
            this.endZ = z;
            this.name = name;
        }
        public String getName() {
        	return name;
        }
        public void setEnd(double x, double z) {
            endX = x;
            endZ = z;
        }
        public double getX() {
            return x;
        }
        public double getZ() {
            return z;
        }
        public double getEndX() {
            return endX;
        }
        public double getEndZ() {
            return endZ;
        }
        public double getSalary() {
            return salary;
        }
        public double getTime() {
            return time;
        }
        public double getBlock() {
            return block;
        }
    }
    private static class Route {
        ArrayList<Job> seq = new ArrayList<Job>();
        private static double getTravelTime(double x1, double z1, double x2, double z2) {
            return Math.sqrt((x1-x2)*(x1-x2)+(z1-z2)*(z1-z2))/6.25;
        }
        public Route() {};
        void add(Job j) {
            seq.add(j);
        }
        // Ein Job darf nicht mehrfach enthalten sein (done)
        boolean routeIsValid() {
            Object[] arraySeq = seq.toArray();
            for(int i = 0; i < arraySeq.length; i++) {
                for(int k = i+1; k < arraySeq.length; k++) {
                    if(Job.isEqual(((Job)arraySeq[i]),((Job)arraySeq[k]))) return false;
                }
            }

            return true;
        }
        String returnRoute() {
        	Job[] arraySeq = new Job[seq.size()];
        	arraySeq = seq.toArray(arraySeq);
        	String retString = "";
        	for(int i = 0; i < arraySeq.length; i++) {
        		retString+=" -> "+arraySeq[i].getName();
        	}
        	retString = retString.replaceFirst(" ->", "");
        	return retString;
        }
        double obtainSalary() {
            Object[] arraySeq = seq.toArray();
            double salary = 0;
            for(Object j : arraySeq) {
                salary += ((Job)j).getSalary();
            }
            return salary;
        }
        double obtainTimePerRun() {
            double time = 0;
            Object[] arraySeq = seq.toArray();
            for(int i = 0; i < arraySeq.length; i++) {
                time+=((Job)arraySeq[i]).getTime();
                if(i<arraySeq.length-1) {
                    time+=Route.getTravelTime(((Job)arraySeq[i]).getEndX(),((Job)arraySeq[i]).getEndZ(),((Job)arraySeq[i+1]).getX(),((Job)arraySeq[i+1]).getZ());
                }
                else {
                    time+=Route.getTravelTime(((Job)arraySeq[i]).getEndX(),((Job)arraySeq[i]).getEndZ(),((Job)arraySeq[0]).getX(),((Job)arraySeq[0]).getZ());
                }

            }
            return time;
        }
        // Berechnet, wie viel Leerlaufzeit im zweiten Durchlauf vorhanden ist 
        /* double obtainSecondRunBlockTime() {
        	double block = 0;
        	double time = obtainTimePerRun();
        	Object[] arraySeq = seq.toArray();
        	for(int i = 0; i < arraySeq.length; i++) {
        		if(time<((Job)arraySeq[i]).getBlock()) block+=(((Job)arraySeq[i]).getBlock()-time);
        	}
        	return block;
        } */
        double obtainHourlySalary() {
            return 3600.0/obtainTimePerRun()*obtainSalary();
        }
        // Generiert ein vollständiges Protokoll für eine Jobroute mit Zeitlänge timeCap (in Sekunden) mit Bezahlung nach bestimmter Zeitangabe
        void generateProtocol(double timeCap, double x, double z) {
        	double time = 0;
        	double salary = 0;
        	Job[] arraySeq = new Job[seq.size()];
        	arraySeq = seq.toArray(arraySeq);
        	time+=getTravelTime(x,z,arraySeq[0].getX(),arraySeq[0].getZ());
        	System.out.println("0:00:00 | Laufe zum ersten Job. Das dauert "+timeString(time)+".");
        	// Gibt jeweils an, ab welcher Zeiteinheit ein bestimmter Job wieder ausführbar ist
        	double[] blocker = new double[arraySeq.length];
        	int currentJobIndex = 0;
        	while(true) {
        		if(time<blocker[currentJobIndex]) {
        			double wait = blocker[currentJobIndex]-time;
        			System.out.println(timeString(time)+" | "+ "Warte insgesamt "+timeString(wait)+".");
        			time = blocker[currentJobIndex];
        		}
        		if(time >= timeCap) {
        			System.out.println(timeString(time)+" | Lange genug gearbeitet, breche ab.");
        			System.out.println("Gesamtgewinn: "+(int)Math.round(salary-(salary%1))+"$.");
        			double hourlyWage = salary/time*3600;
        			System.out.println("Stundenlohn: "+(int)Math.round(hourlyWage-(hourlyWage%1))+"$");
        			return;
        		}
        		salary+=arraySeq[currentJobIndex].getSalary();
        		blocker[currentJobIndex]=time+arraySeq[currentJobIndex].getBlock();
        		System.out.println(timeString(time) +" | Arbeite insgesamt "+timeString(arraySeq[currentJobIndex].getTime())+" lang als "+arraySeq[currentJobIndex].getName()+" für "+(int)Math.round(arraySeq[currentJobIndex].getSalary()-(arraySeq[currentJobIndex].getSalary()%1))+"$. Derzeitiger Stand: "+(int)Math.round(salary-(salary%1))+"$");
        		time+=arraySeq[currentJobIndex].getTime();
        		double timeToNextJob = 0;
        		if(currentJobIndex<arraySeq.length-1) {
        			timeToNextJob = Route.getTravelTime(arraySeq[currentJobIndex].getEndX(),arraySeq[currentJobIndex].getEndZ(),arraySeq[currentJobIndex+1].getX(),arraySeq[currentJobIndex+1].getZ());
        		}
        		else {
        			timeToNextJob = Route.getTravelTime(arraySeq[currentJobIndex].getEndX(),arraySeq[currentJobIndex].getEndZ(),arraySeq[0].getX(),arraySeq[0].getZ());
        		}
        		currentJobIndex++;
        		if(currentJobIndex==arraySeq.length) currentJobIndex = 0; 
        		if(time + timeToNextJob >= timeCap || ((time + timeToNextJob < blocker[currentJobIndex])&&blocker[currentJobIndex]>=timeCap)) {
        			System.out.println(timeString(time)+" | Lange genug gearbeitet, breche ab.");
        			System.out.println("Gesamtgewinn: "+(int)Math.round(salary-(salary%1))+"$.");
        			double hourlyWage = salary/time*3600;
        			System.out.println("Stundenlohn: "+(int)Math.round(hourlyWage-(hourlyWage%1))+"$");
        			return;
        		}
        		System.out.println(timeString(time)+" | Laufe zum nächsten Job. Das dauert "+timeString(timeToNextJob)+".");
        		time+=timeToNextJob;
        	}
        }
        int generateSilentProtocol(double timeCap, int x, int z) {
        	double time = 0;
        	double salary = 0;
        	Job[] arraySeq = new Job[seq.size()];
        	arraySeq = seq.toArray(arraySeq);
        	time+=getTravelTime(x,z,arraySeq[0].getX(),arraySeq[0].getZ());
        	// Gibt jeweils an, ab welcher Zeiteinheit ein bestimmter Job wieder ausführbar ist
        	double[] blocker = new double[arraySeq.length];
        	int currentJobIndex = 0;
        	while(true) {
        		if(time<blocker[currentJobIndex]) {
        			double wait = blocker[currentJobIndex]-time;
        			//System.out.println(timeString(time)+" | "+ "Warte insgesamt "+timeString(wait)+".");
        			time = blocker[currentJobIndex];
        		}
        		if(time >= timeCap) {
        			//System.out.println(timeString(time)+" | Lange genug gearbeitet, breche ab.");
        			//System.out.println("Gesamtgewinn: "+(int)Math.round(salary-(salary%1))+"$.");
        			double hourlyWage = salary/time*3600;
        			//System.out.println("Stundenlohn: "+(int)Math.round(hourlyWage-(hourlyWage%1))+"$");
        			return (int)Math.round(hourlyWage-(hourlyWage%1));
        		}
        		salary+=arraySeq[currentJobIndex].getSalary();
        		blocker[currentJobIndex]=time+arraySeq[currentJobIndex].getBlock();
        		//System.out.println(timeString(time) +" | Arbeite insgesamt "+timeString(arraySeq[currentJobIndex].getTime())+" lang als "+arraySeq[currentJobIndex].getName()+" für "+(int)Math.round(arraySeq[currentJobIndex].getSalary()-(arraySeq[currentJobIndex].getSalary()%1))+"$. Derzeitiger Stand: "+(int)Math.round(salary-(salary%1))+"$");
        		time+=arraySeq[currentJobIndex].getTime();
        		double timeToNextJob = 0;
        		if(currentJobIndex<arraySeq.length-1) {
        			timeToNextJob = Route.getTravelTime(arraySeq[currentJobIndex].getEndX(),arraySeq[currentJobIndex].getEndZ(),arraySeq[currentJobIndex+1].getX(),arraySeq[currentJobIndex+1].getZ());
        		}
        		else {
        			timeToNextJob = Route.getTravelTime(arraySeq[currentJobIndex].getEndX(),arraySeq[currentJobIndex].getEndZ(),arraySeq[0].getX(),arraySeq[0].getZ());
        		}
        		currentJobIndex++;
        		if(currentJobIndex==arraySeq.length) currentJobIndex = 0; 
        		if(time + timeToNextJob >= timeCap || ((time + timeToNextJob < blocker[currentJobIndex])&&blocker[currentJobIndex]>=timeCap)) {
        			//System.out.println(timeString(time)+" | Lange genug gearbeitet, breche ab.");
        			//System.out.println("Gesamtgewinn: "+(int)Math.round(salary-(salary%1))+"$.");
        			double hourlyWage = salary/time*3600;
        			//System.out.println("Stundenlohn: "+(int)Math.round(hourlyWage-(hourlyWage%1))+"$");
        			return (int)Math.round(hourlyWage-(hourlyWage%1));
        		}
        		//System.out.println(timeString(time)+" | Laufe zum nächsten Job. Das dauert "+timeString(timeToNextJob)+".");
        		time+=timeToNextJob;
        	}
        }
    }
    static String timeString(double time) {
    	int h = 0;
    	int m = 0;
    	int s = 0;
    	while(time>=3600) {
    		time-=3600;
    		h++;
    	}
    	while(time>=60) {
    		time-=60;
    		m++;
    	}
    	time = time-(time%1);
    	s = (int)Math.round(time);
    	String M = m+"";
    	String S = s+"";
    	if(m<10) M="0"+M;
    	if(s<10) S="0"+S;
    	return h+":"+M+":"+S;
    }
    public static long runTime = 0;
    public static Route optimalRoute = new Route();
    public static int optimalSalary = 0;
    public static void findOptimalRoutes(Job[] array, double timeCap, int x, int z) {
    	boolean[] subset = new boolean[array.length];
    	for(int i = 0; i < subset.length; i++) subset[i] = false;
    	generateSubset(array, timeCap, subset, 1, x, z);
    	subset[0] = true;
    	generateSubset(array, timeCap, subset, 1, x, z);
    	System.out.println("Getestete Routen: "+runTime+".");
    }
    public static void generateSubset(Job[] array, double timeCap, boolean[] currentSubset, int currentIndex, int x, int z) {
    	boolean[] copy = currentSubset.clone();
    	if(currentIndex<currentSubset.length-1) {
    		generateSubset(array, timeCap, copy, currentIndex+1, x, z);
    		copy[currentIndex] = true;
    		generateSubset(array, timeCap, copy, currentIndex+1, x, z);
    	}
    	else {
    		finalizeSubset(array, timeCap, copy, x, z);
    		copy[currentIndex] = true;
    		finalizeSubset(array, timeCap, copy, x, z);
    	}
    }
    public static void finalizeSubset(Job[] array, double timeCap, boolean[] subset, int x, int z) {
    	ArrayList<Job> al = new ArrayList<Job>();
    	int counter = 0;
    	for(int i = 0; i < subset.length; i++) {
    		if(subset[i]) {
    			counter++;
    			al.add(array[i]);
    		}
    	}
    	Job[] jobs = new Job[al.size()];
    	jobs = al.toArray(jobs);
    	for(int i = 0; i < jobs.length; i++) {
    		Job[] jobCopy = jobs.clone();
    		jobCopy[i] = null;
    		ArrayList<Job> currentSubset = new ArrayList<Job>();
    		currentSubset.add(jobs[i]);
    		shuffleSubset(timeCap,jobCopy,currentSubset, x, z);
    	}
    }
    public static ArrayList<Job> clonedArrayList(ArrayList<Job> jobList) {
    	ArrayList<Job> c = new ArrayList<Job>();
    	Job[] jobs = new Job[jobList.size()];
    	jobs = jobList.toArray(jobs);
    	for(int i = 0; i < jobs.length; i++) {
    		c.add(jobs[i]);
    	}
    	return c;
    }
    public static void shuffleSubset(double timeCap, Job[] jobs, ArrayList<Job> currentSubset, int x, int z) {
    	boolean unusedJobFound = false;
    	for(int i = 0; i < jobs.length; i++) {
    		if(jobs[i]==null) continue;
    		unusedJobFound = true;
        	ArrayList<Job> subsetCopy = clonedArrayList(currentSubset);
        	Job[] jobCopy = jobs.clone();
        	jobCopy[i] = null;
        	subsetCopy.add(jobs[i]);
        	shuffleSubset(timeCap, jobCopy, subsetCopy, x, z);
    	}
    	if(!unusedJobFound) testSubset(timeCap, currentSubset, x, z);
    }
    public static void testSubset(double timeCap, ArrayList<Job> subset, int x, int z) {
    	runTime++;
    	Job[] jobs = new Job[subset.size()];
    	jobs = subset.toArray(jobs);
    	Route r = new Route();
    	for(int i = 0; i < jobs.length; i++) {
    		r.add(jobs[i]);
    	}
    	int hSalary = r.generateSilentProtocol(timeCap, x, z);
    	if(hSalary>optimalSalary) {
    		optimalSalary = hSalary;
    		System.out.println("Optimalere Route ("+hSalary+"$) gefunden:"+r.returnRoute()+".");
    	}
    }
}
