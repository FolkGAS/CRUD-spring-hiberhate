package userstable.model;

public class UsersFilter {
    private Integer idStart, idEnd;
    private String name;
    private Integer ageStart, ageEnd;
    private boolean isAdmin;
    private String dateStart, dateEnd;
    private int page, entriesPerPage;

    public Integer getIdStart() {
        return idStart;
    }

    public void setIdStart(Integer idStart) {
        this.idStart = idStart;
    }

    public Integer getIdEnd() {
        return idEnd;
    }

    public void setIdEnd(Integer idEnd) {
        this.idEnd = idEnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAgeStart() {
        return ageStart;
    }

    public void setAgeStart(Integer ageStart) {
        this.ageStart = ageStart;
    }

    public Integer getAgeEnd() {
        return ageEnd;
    }

    public void setAgeEnd(Integer ageEnd) {
        this.ageEnd = ageEnd;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getEntriesPerPage() {
        return entriesPerPage;
    }

    public void setEntriesPerPage(int entriesPerPage) {
        this.entriesPerPage = entriesPerPage;
    }

    @Override
    public UsersFilter clone(){
        UsersFilter clone = new UsersFilter();
        if (idStart != null)
            clone.setIdStart(idStart);
        if (idEnd != null)
            clone.setIdEnd(idEnd);
        if (name != null)
            clone.setName(new String(name));
        if (ageStart != null)
            clone.setAgeStart(ageStart);
        if (ageEnd != null)
            clone.setAgeEnd(ageEnd);
        if (dateStart != null)
            clone.setDateStart(dateStart);
        if (dateEnd != null)
            clone.setDateEnd(dateEnd);
        clone.setAdmin(isAdmin);
        clone.setPage(page);
        clone.setEntriesPerPage(entriesPerPage);
        return clone;
    }
}
