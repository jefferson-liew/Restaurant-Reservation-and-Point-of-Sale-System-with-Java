package oodp.data;
/**
 * Staff
 */
public class Staff extends Person {
	String staffId;
	String jobTitle;
	/**
	 * Empty constructor for DataHandler to access class name
	 */
	public Staff() {
		super();
	}
	/**
	 * Constructor for Staff
	 * @param staffId this Staff's staffId
	 * @param name this Staff's name
	 * @param gender this Staff's gender
	 * @param jobTitle this Staff's job title
	 */
	public Staff(String staffId, String name, String gender, String jobTitle) {
		super();
		this.staffId = staffId;
		this.name = name;
		this.gender = gender;
		this.jobTitle = jobTitle;
	}
	/**
	 * Gets the name of this Staff
	 * @return this Staff's name
	 */
	public String getStaffName() {
		return name;
	}
	/**
	 * Gets the staffId of this Staff
	 * @return this Staff's staffId
	 */
	public String getStaffId() {
		return staffId;
	}
	/**
	 * Sets the staffId of this Staff
	 * @param staffId this Staff's staffId
	 */
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	/**
	 * Gets the job title of this Staff
	 * @return this Staff's job title
	 */
	public String getJobTitle() {
		return jobTitle;
	}

	/**
	 * Sets the job title of this Staff
	 * @param jobTitle this Staff's job title
	 */
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
}
