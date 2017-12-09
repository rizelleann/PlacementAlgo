/*Rizelle Ann Bahin*/

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;


public class PlacementAlgorithms {

    private static Scanner sc;

    public static void main(String[] args) throws InterruptedException, IOException {   
        int choice = 0;
        sc = new Scanner(System.in);

        do {
            System.out.println("\n\n\t\t\t\t\t\t[1] FIRST-FIT ALGORITHM\n\t\t\t\t\t\t[2] BEST-FIT ALGORITHM \n\t\t\t\t\t\t[3] WORST-FIT ALGORITHM \n\t\t\t\t\t\t[4] EXIT\n\n");
            System.out.print("\t\t\t\t\t\t>>>  ");
            choice = sc.nextInt();
            if(!(choice >= 1 && choice <= 4)) {
                while( choice < 1 || choice > 4) {
                    System.out.println("\t\t\t\t\t\tInput is not one of the choices.. ");
                    System.out.print("\t\t\t\t\t\t>>>  ");
                    choice = sc.nextInt();
                }
            }
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

            ArrayList<MemPartitions> partitions = new ArrayList<MemPartitions>();
            
            partitions.add(new MemPartitions(1, 9500));
            partitions.add(new MemPartitions(2, 7000));
            partitions.add(new MemPartitions(3, 4500));
            partitions.add(new MemPartitions(4, 8500));
            partitions.add(new MemPartitions(5, 3000));
            partitions.add(new MemPartitions(6, 9000));
            partitions.add(new MemPartitions(7, 1000));
            partitions.add(new MemPartitions(8, 5500));
            partitions.add(new MemPartitions(9, 1500));
            partitions.add(new MemPartitions(10, 500));
            
    
            ArrayList<Job> jobs = new ArrayList<Job>();
            
            jobs.add(new Job(1, 5, 5760));
            jobs.add(new Job(2, 4, 4190));
            jobs.add(new Job(3, 8, 3290));
            jobs.add(new Job(4, 2, 2030));
            jobs.add(new Job(5, 2, 2550));
            jobs.add(new Job(6, 6, 6990));
            jobs.add(new Job(7, 8, 8940));
            jobs.add(new Job(8, 10, 740));
            jobs.add(new Job(9, 7, 3930));
            jobs.add(new Job(10, 6,6890));
            jobs.add(new Job(11, 5, 6580));
            jobs.add(new Job(12, 8, 3820));
            jobs.add(new Job(13, 9, 9140));
            jobs.add(new Job(14, 10, 420));
            jobs.add(new Job(15, 10, 220));
            jobs.add(new Job(16, 7, 7540));
            jobs.add(new Job(17, 3, 3210));
            jobs.add(new Job(18, 1, 1380));
            jobs.add(new Job(19, 9, 9850));
            jobs.add(new Job(20, 3, 3610));
            jobs.add(new Job(21, 7, 7540));
            jobs.add(new Job(22, 2, 2710));
            jobs.add(new Job(23, 8, 8390));
            jobs.add(new Job(24, 5, 5950));
            jobs.add(new Job(25, 10, 760));
            
            switch(choice) {
                case 1:
                    partitions.sort(new CompMemBlock());
                    display(partitions,jobs, "FIRST-FIT ALGORITHM");
                    break;
                case 2:
                    partitions.sort(new CompMemSizeI());
                    display(partitions,jobs, "BEST-FIT ALGORITHM" );
                    break;
                case 3:
                    partitions.sort(new CompMemSizeD());
                    display(partitions,jobs, "WORST-FIT ALGORITHM");
                    break;
                case 4:
                    System.out.println("\n\n=================================================== === === =============================================================");
                    System.out.println("\t\t\t\t\t\tProgram Terminated");
                    System.out.println("=================================================== === === =============================================================");
                    System.exit(0);
                    break;
            }
        } while(choice >= 1 && choice <= 3);
    } 
     private static void display(ArrayList<MemPartitions> partitions,ArrayList<Job> jobs, String algorithm) throws InterruptedException, IOException{
            ArrayList<Job> finished = new ArrayList<Job>();
            ArrayList<Job> cjobs = allocateJob(jobs, partitions);
           
           int fjobs = cjobs.size() - 1;
            int ctr = 0;
            boolean flag = false;
            do{
                int tempSize = cjobs.size();
                int tempctr = 0;
                do{
                    
                    Job tempJob = cjobs.get(0);
                    for(int j = 0; j < partitions.size(); j++){
                        if(partitions.get(j).getStatus() == true && tempJob.getJobSize() < partitions.get(j).getMemSize()){
                            partitions.get(j).setJob(tempJob);
                            partitions.get(j).setStatus(false);
                            partitions.get(j).setFrag(partitions.get(j).getMemSize() - tempJob.getJobSize());
                            cjobs.remove(0);
                            flag = true;
                            break;
                        }
                    }
                    if(!flag){
                        cjobs.remove(0);
                        cjobs.add(tempJob);
                    }
                    flag = false;
                    tempctr++;
                }while(tempctr < tempSize);
                //Collections.sort(partitions,new CompMemBlock());
                System.out.println("=== --- ==== --- === --- === --- === --- === --- === --- === --- === --- === --- === --- === --- === --- === --- === ---");
                System.out.println("\n\t\t\t\t\t\t" + algorithm + "\n");
                System.out.println("=== --- ==== --- === --- === --- === --- === --- === --- === --- === --- === --- === --- === --- === --- === --- === ---");
                System.out.println("\t\tMEMORY BLOCK" + "\t" + "MEMORY SIZE" + "\t" + "JOB" + "\t   JOB SIZE" + "\tCPU TIME(ms)" + "\t" + "INTERNAL FRAGMENTATION\n");
                for(int i = 0; i < partitions.size(); i++){
                    if(partitions.get(i).getJob() == null){
                        System.out.println("\t\t   " + partitions.get(i).getMemBlock() + "\t\t  " + partitions.get(i).getMemSize());
                    }else{
                        System.out.println("\t\t   " + partitions.get(i).getMemBlock() + "\t\t  " + partitions.get(i).getMemSize() + "\t\tJob " + partitions.get(i).getJob().getJobStreamNum() + "\t     " + partitions.get(i).getJob().getJobSize() + " \t   " + partitions.get(i).getJob().getTime()  + "\t\t  " + partitions.get(i).getFrag());
                    }
                }
                throughput(ctr, cjobs.size(), jobs.size());
                storageUtil(partitions);
                waitingQueue(cjobs);
                //EXECUTION
                ctr++;
                for(int i = 0; i < cjobs.size(); i++){
                    cjobs.get(i).setWaitingTime(cjobs.get(i).getWaitingTime() + 1);
                }
                for(int i = 0; i < partitions.size(); i++){
                    if(partitions.get(i).getJob() != null){
                        partitions.get(i).getJob().setTime(partitions.get(i).getJob().getTime() - 1);
                        if(partitions.get(i).getJob().getTime() == 0){
                            finished.add(partitions.get(i).getJob());
                            partitions.get(i).setFrag(0);
                            partitions.get(i).setJob(null);
                            partitions.get(i).setStatus(true);
                        }
                    }
                }

               Thread.sleep(1000);
               new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
               
            }while(finished.size() < fjobs);
            //Collections.sort(partitions,new CompMemBlock());
            System.out.println("=== --- ==== --- === --- === --- === --- === --- === --- === --- === --- === --- === --- === --- === --- === --- === ---");
            System.out.println("\n\t\t\t\t\t\t" + algorithm + "\n");
            System.out.println("=== --- ==== --- === --- === --- === --- === --- === --- === --- === --- === --- === --- === --- === --- === --- === ---");
            System.out.println("\t\tMEMORY BLOCK" + "\t" + "MEMORY SIZE" + "\t" + "JOB" + "\t   JOB SIZE" + "\tCPU TIME(ms)" + "\t" + "INTERNAL FRAGMENTATION\n");
            for(int i = 0; i < partitions.size(); i++){
                if(partitions.get(i).getJob() == null){
                    System.out.println("\t\t   " + partitions.get(i).getMemBlock() + "\t\t  " + partitions.get(i).getMemSize());
                }else{
                    System.out.println("\t\t   " + partitions.get(i).getMemBlock() + "\t\t  " + partitions.get(i).getMemSize() + "\t\tJob " + partitions.get(i).getJob().getJobStreamNum() + "\t     " + partitions.get(i).getJob().getJobSize() + " \t   " + partitions.get(i).getJob().getTime()  + "\t\t  " + partitions.get(i).getFrag());
                }
            }
            
            throughput(ctr, cjobs.size(), jobs.size());
            storageUtil(partitions);
            waitingQueue(cjobs);
        }
  
    private static ArrayList<Job> allocateJob(ArrayList<Job> jobs, ArrayList<MemPartitions> partitions){ //removes jobs that cannot be allocated
        ArrayList<Job> cjobs = new ArrayList<Job>();
        ArrayList<Integer> unAllocated = new ArrayList<Integer>();
        int max = maxMem(partitions);
        
        for(int i = 0; i < jobs.size(); i++){
            if(jobs.get(i).getTime() > max){
                unAllocated.add(i);
            }
        }
        for(int i = 0; i < jobs.size(); i++){
            cjobs.add(jobs.get(i));
        }
        for(int i = 0; i < unAllocated.size(); i++){
            cjobs.remove(unAllocated.get(i));
        }
        return cjobs;
    }
   
    private static int maxMem(ArrayList<MemPartitions> partitions){
        int max = 0;
        for(int i = 0; i < partitions.size(); i++){
            if(max < partitions.get(i).getMemSize()){
                max = partitions.get(i).getMemSize();
            }
        }
        return max;
    }
    
    private static void storageUtil(ArrayList<MemPartitions> partitions){
        double jobTotal = 0.0;
        double totalmem = 0.0;
        for(int i = 0; i < partitions.size(); i++){
            if(partitions.get(i).getJob() != null){
                jobTotal = jobTotal + (double) partitions.get(i).getJob().getJobSize();
            }
            totalmem += (double) partitions.get(i).getMemSize();
        }
        System.out.println("Storage Utilization:\t" + (jobTotal / totalmem) * 100 + " %");
    }

    private static void throughput(int t, int ua, int j){
        double time = (double) t, unAllocated = (double) ua, jobs = (double) j;
        double tp = (jobs - unAllocated) / time;
//      System.out.println("");
        System.out.println("\n\nThroughput:\t\t" + tp + " jobs/ms");
    }
    
    private static void waitingQueue(ArrayList<Job> cjobs){
            System.out.println("== == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == ");
            System.out.println("\n\t\t\t\t\t\tW A I T I N G   Q U E U E "+"\n\t\t\t\t\t\twaiting queue length:  "+ cjobs.size() + "\n");
            System.out.println("== == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == == ");
            System.out.println("\t\t\t\t\t\tJOB\t\tWAITING TIME(ms)\n");
            for(int i = 0; i < cjobs.size(); i++){
                System.out.println("\t\t\t\t\t\tJob " + cjobs.get(i).getJobStreamNum() + "\t\t   " + cjobs.get(i).getWaitingTime() +" ms");
            }
    }
   
}
class CompMemBlock implements Comparator<MemPartitions> {
    @Override
    public int compare(MemPartitions m1, MemPartitions m2) {
        return m1.getMemBlock() - m2.getMemBlock();
    }
}
class CompMemSizeI implements Comparator<MemPartitions> {
    @Override
    public int compare(MemPartitions m1, MemPartitions m2) {
        return m1.getMemSize() - m2.getMemSize();
    }
}
class CompMemSizeD implements Comparator<MemPartitions> {
    @Override
    public int compare(MemPartitions m1, MemPartitions m2) {
        return m2.getMemSize() - m1.getMemSize();
    }
}
class MemPartitions{
    private int memBlock, memSize, internalFrag;
    Job job;
    private boolean status;
    
    MemPartitions(int memBlock, int memSize){
        this.memBlock = memBlock;
        this.memSize = memSize;
        this.status = true;
        this.internalFrag = 0;
        this.job = null;
    }
    public void setMemBlock(int memBlock){
        this.memBlock = memBlock;
    }

    public int getMemBlock(){
        return this.memBlock;
    }

    public void setMemSize(int memSize){
        this.memSize = memSize;
    }

    public int getMemSize(){
        return this.memSize;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public boolean getStatus(){
        return this.status;
    }
    public void setFrag(int internalFrag){
        this.internalFrag = internalFrag;
    }
    
    public int getFrag(){
        return this.internalFrag;
    }
    
    public void setJob(Job job){
        this.job = job;
    }
    
    public Job getJob(){
        return this.job;
    }
}
class Job{
    private int jobStreamNum, time, jobSize, waitingTime;
    
    Job(int jobStreamNum, int time, int jobSize){
        this.jobStreamNum = jobStreamNum;
        this.time = time;
        this.jobSize = jobSize;
        this.waitingTime = 0;
    }
    public void setJobStreamNum(int jobStreamNum){
        this.jobStreamNum = jobStreamNum;
    }

    public int getJobStreamNum(){
        return this.jobStreamNum;
    }
    
    public void setTime(int time){
        this.time = time;
    }
    
    public int getTime(){
        return this.time;
    }
    
    public void setJobSize(int jobSize){
        this.jobSize = jobSize;
    }
    
    public int getJobSize(){
        return this.jobSize;
    }
    
    public void setWaitingTime(int waitingTime){
        this.waitingTime = waitingTime;
    }
    
    public int getWaitingTime(){
        return this.waitingTime;
    }
}
