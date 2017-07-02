package springbook.user.domain;

public interface UserLevelUpgradePolicy {
	public boolean canUpgradeLevel(User user);
	public void upgradeLevel(User user);
}
