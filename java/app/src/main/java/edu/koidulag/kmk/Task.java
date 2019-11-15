package edu.koidulag.kmk;

//TODO  -   Lisa ülesanded ja implement them.
public enum Task {

    FINDTEAM(1, "Leia oma tiimiliikmed ja lisage tiimile nimi."),
    TRIVIA(2, "Sooritage tiimiga väike küsimustik kooli kohta, lubadtud on ka küsida abi õpetajate käest.");

    int id;
    String name;

    Task(int _id, String dname) {
        id = _id;
        name = dname;
    }

}
