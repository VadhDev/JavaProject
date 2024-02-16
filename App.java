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

        // Assign the task to the first member with matching skill
        for (TeamMember member : teamMembers) {
            if (member.getSkill().equals(requiredSkill) && member.hasAvailableTaskSlot()) {
                member.decrementTaskSlots();
                member.addTask(task);
                break;
            }
        }
    }

    public void displayMembers() {
        System.out.println("Team Members:");
        for (TeamMember member : teamMembers) {
            System.out.println("- " + member.getName() + " (Skill: " + member.getSkill() + ", Task Slots: " + member.getTaskSlots() + ")");
            List<Task> memberTasks = member.getTasks();
            if (memberTasks.isEmpty()) {
                System.out.println("   No tasks assigned.");
            } else {
                System.out.println("   Assigned Tasks:");
                for (Task task : memberTasks) {
                    System.out.println("      - " + task.getName());
                }
            }
        }
        System.out.println();
    }
}

public class App {
    public static void main(String[] args) {
        TaskAllocator taskAllocator = new TaskAllocator();
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("Menu:");
            System.out.println("1. Add Team Member");
            System.out.println("2. Add Task");
            System.out.println("3. Display Team Members");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter member name: ");
                    String memberName = scanner.nextLine();
                    System.out.print("Enter member skill: ");
                    String memberSkill = scanner.nextLine();
                    taskAllocator.addTeamMember(memberName, memberSkill);
                    System.out.println("Member added.");
                    break;
                case 2:
                    System.out.print("Enter task name: ");
                    String taskName = scanner.nextLine();
                    System.out.print("Enter required skill for task: ");
                    String taskSkill = scanner.nextLine();
                    taskAllocator.addTask(taskName, taskSkill);
                    System.out.println("Task added.");
                    break;
                case 3:
                    taskAllocator.displayMembers();
                    break;
                case 4:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose a valid option.");
            }
        }

        scanner.close();
    }
}
