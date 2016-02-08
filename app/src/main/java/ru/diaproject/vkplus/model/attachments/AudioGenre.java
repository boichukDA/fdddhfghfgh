package ru.diaproject.vkplus.model.attachments;

public enum AudioGenre {
    ROCK("Rock",1),
    POP("Pop", 2),
    RAP_AND_HIPHOP("Rap & Hip-Hop",3),
    EASY_LISTENING("Easy Listening", 4),
    DANCE_AND_HOUSE("Dance & House",5),
    INSTRUMENTAL("Instrumental", 6),
    METAL("Metal", 7),
    DUBSTEP("Dubstep",8),
    JAZZ_AND_BLUES("Jazz & Blues", 9),
    ALTERNATIVE("Alternative",21),
    DRUM_AND_BASS("Drum & Bass",10),
    TRANCE("Trance", 11),
    CHANSON("Chanson",12),
    ETHNIC("Ethnic",13),
    ACOUSTIC_AND_VOCAL("Acoustic & Vocal",14),
    REGGAE("Reggae",15),
    CLASSICAL("Classical",16),
    INDIE_POP("Indie Pop",17),
    SPEECH("Speech",19),
    ELECTROPOP_AND_DISCO("Electropop & Disco",22),
    OTHER("Other", 18),
    NOT_SET("",0);

    public static  AudioGenre valueOf(Integer id){
        for (AudioGenre value:values())
            if (value.getId().equals(id))
                return value;
        return NOT_SET;
    }
    private String name;
    private Integer id;

    AudioGenre(String name, Integer id){
        setId(id);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
