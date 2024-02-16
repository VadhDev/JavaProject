import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class TeamMember {
    private String name;
    private String skill;
    private int taskSlots;
    private List<Task> tasks;

    public TeamMember(String name, String skill) {
        this.name = name;
        this.skill = skill;
        this.taskSlots = 3; // Set the initial number of task slots to 3
        this.tasks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getSkill() {
        return skill;
    }

    public int getTaskSlots() {
        return taskSlots;
    }

    public void decrementTaskSlots() {
        taskSlots--;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public boolean hasAvailableTaskSlot() {
        return taskSlots > 0;
    }
}

class Task {
    private String name;
    private String requiredSkill;
    private boolean completed;

    public Task(String name, String requiredSkill) {
        this.name = name;
        this.requiredSkill = requiredSkill;
        this.completed = false;
    }

    public String getName() {
        return name;
    }

    public String getRequiredSkill() {
        return requiredSkill;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        completed = true;
    }
}

class TaskAllocator {
    private List<TeamMember> teamMembers;
    private List<Task> tasks;

    public TaskAllocator() {
        teamMembers = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    public void addTeamMember(String name, String skill) {
        TeamMember member = new TeamMember(name, skill);
        teamMembers.add(member);
    }

    public void addTask(String name, String requiredSkill) {
        Task task = new Task(name, requiredSkill);
        tasks.add(task);
    }

    public void displayAssignedTasks() {
        for (TeamMember member : teamMembers) {
            System.out.println(member.getName() + " - Skill: " + member.getSkill() + " - Task Slots: " + member.getTaskSlots());
            System.out.println("Assigned Tasks:");
            List<Task> memberTasks = member.getTasks();
            if (memberTasks.isEmpty()) {
                System.out.println("No tasks assigned.");
            } else {
                for (Task task : memberTasks) {
                    System.out.println("- " + task.getName());
                }
            }
            System.out.println();
        }
    }

    public void assignTasks() {
        for (Task task : tasks) {
            boolean taskAssigned = false;
            for (TeamMember member : teamMembers) {
                if (member.hasAvailableTaskSlot() && member.getSkill().equals(task.getRequiredSkill())) {
                    member.decrementTaskSlots();
                    member.addTask(task);
                    taskAssigned = true;
                    break;
                }
            }
            if (!taskAssigned) {
                System.out.println("No available team member for task \"" + task.getName() + "\"");
            }
        }
    }
}

public class App {
    public static void main(String[] args) {
        TaskAllocator taskAllocator = new TaskAllocator();
        Scanner scanner = new Scanner(System.in);

        // Add predefined team members
        taskAllocator.addTeamMember("Vadh", "FrontEnd");
        taskAllocator.addTeamMember("Nak", "BackEnd");
        taskAllocator.addTeamMember("Hak", "Sales");

        int taskSlots = 3; // Set the number of initial task slots for each member

        // User input for new tasks
        System.out.print("Enter the number of tasks you want to add: ");
        int numberOfTasks = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < numberOfTasks; i++) {
            System.out.print("Enter name of task " + (i + 1) + ": ");
            String taskName = scanner.nextLine();
            System.out.print("Enter required skill for task " + (i + 1) + ": ");
            String requiredSkill = scanner.nextLine();
            taskAllocator.addTask(taskName, requiredSkill);
        }

        // Assign tasks
        taskAllocator.assignTasks();

        // Display assigned tasks for each team member
        taskAllocator.displayAssignedTasks();

        scanner.close();
    }
}