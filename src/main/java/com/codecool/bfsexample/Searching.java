package com.codecool.bfsexample;

import com.codecool.bfsexample.model.UserNode;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Searching {
    private List<UserNode> users;


    public Searching(List<UserNode> dao) {
        this.users = dao;
    }

    public static void main(String[] args) {
        RandomDataGenerator generator = new RandomDataGenerator();
        List<UserNode> users = generator.generate();

        System.out.println(users.toString());
        Searching sc = new Searching(users);



        Scanner scanner = new Scanner(System.in);
        System.out.println("for minimum distance press: 1" +
                "for  user finding press: 2");

        String s = scanner.nextLine();
        boolean isTrue = true;
        while (isTrue) {
            s = scanner.nextLine();
            if (s.equals("1") || s.equals("2")) {
                isTrue = false;
            }
        }

        switch (s) {
            case "1":
                System.out.println("First user:");
                UserNode firstUser = sc.getUserByInput();

                System.out.println("Second user:");
                UserNode secondUser = sc.getUserByInput();

                System.out.print("Distance is: ");
                System.out.println(sc.getMinDistanceBetweenUsers(firstUser, secondUser));
                break;
            case "2":
                System.out.println("User to find friends:");
                UserNode user = sc.getUserByInput();
                System.out.print("Depth: ");
                int depth = Integer.parseInt(new Scanner(System.in).nextLine());

                Set<UserNode> friends = sc.getFriendsByDepth(user, depth);
                System.out.println("Friends:");
                friends.forEach(System.out::println);
                break;
        }

    }

    private UserNode getUserByInput() {
        UserNode user = null;
        while (user == null) {
            user = getUserByFullName(new Scanner(System.in).nextLine());
        }
        return user;
    }

    private UserNode getUserByFullName(String nextLine) {
        String[] splittedName = nextLine.split(" ");
        UserNode userNode = null;
        for (UserNode user : users
        ) {
            if (user.getFirstName().equals(splittedName[0]) && user.getLastName().equals(splittedName[1])) {
                userNode = user;
            }
        }
        return userNode;
    }

    public int getMinDistanceBetweenUsers(UserNode firstUser, UserNode secondUser) {

        BreadthFirstSearch bfs = new BreadthFirstSearch(firstUser);

        while (bfs.hasNext()) {
            if (bfs.next().equals(secondUser)) return bfs.getDepth();
        }

        return 0;
    }

    public Set<UserNode> getFriendsByDepth(UserNode user, int depth) {

        BreadthFirstSearch bfs = new BreadthFirstSearch(user);
        Set<UserNode> friends = new HashSet<>();

        while (bfs.hasNext()) {
            UserNode currentUser = bfs.next();
            if (bfs.getDepth() > depth) break;
            friends.add(currentUser);
        }

        friends.remove(user);
        return friends;
    }
}
