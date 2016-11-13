package ru.spbstu.icc.kspt.inspace.model.utils;


import ru.spbstu.icc.kspt.inspace.model.Planet;
//import ru.spbstu.icc.kspt.inspace.model.Resources;


abstract public class UpgradeDepartment {

    protected Planet planet;
    private Upgrade upgrade;

    //TODO подумать о том, чтобы хранить ссылку на Upgradable.
    //TODO Upgradable используется в нескольких методах, поэтому, считаю, что добавление ссылки на него
    //TODO повысило бы связность класса. И не пришлось бы в методах кроме startUpgrade иметь аргумент Upgradable.
    //private Upgradable upgradable = null;

    public UpgradeDepartment(Planet planet) {
        this.planet = planet;
    }

    protected boolean checkUpgradability(Upgradable upgradable) {
        //return (areEnoughResourcesToUpgrade(upgradable) && upgrade == null);
        return (planet.getResources().areMoreThan(upgradable.getUpgradeCost()) && upgrade == null);
    }

    protected void startUpgrade(Upgrade upgrade) {

        Upgradable upgradable = upgrade.getUpgradable();
        //TODO возможно стоит задуматься над тем, чтобы метод checkUpgradability выбрасывал сам исключение, если надо
        //TODO по имени скорее подходит именно это, нежели возвращение boolean значения.

        //TODO если оставить как есть, то, наверное, более понятно будет isReadyToUpgrade или isReady
        //TODO или canBeUpgraded, как уже есть на более высоком уровне.
        if (!checkUpgradability(upgradable)) {
            //TODO exception
            return;
        }

        planet.getResources().takeResources(upgradable.getUpgradeCost());
        this.upgrade = upgrade;
    }

    public Upgrade getCurrentUpgrade() {
        return upgrade;
    }


    //TODO Может, стоит разделить на два метода: было ли обновлено и обновить?
    //TODO просто не очень просто понять, когда метод обновить еще и возрващает значение логическое, равное тому, что
    //TODO стоило ли производить обновление.
    protected boolean update() {
        boolean update = upgrade != null && upgrade.getTime().compareTo(Time.now()) <= 0;
        if (update) {
            upgrade.execute(Time.now());
            upgrade = null;
            //когда добавил это, тесты не упали.
            //update = false;
        }
        //TODO возвращаемое значение update соответствует состоянию upgrade до произведения над ним операций.
        //TODO на мой взгляд как-то логичнее возвращать уже новое значение update. Может, так только со стороны кажется.
        //TODO может так, как ты делаешь, удобнее в конечном счете, не знаю.

        //TODO UPD: увидел, что этот метод из наследников вызывается.
        //TODO поэтому было решено так сделать.

        //TODO есть альтернативный вариант:
        //TODO сделать метод например boolean wasUpdated. и update
        //TODO а в наследниках в методах updateSmthng, если !wasUpdated, вызывать super.update и добавалять свое что-то.
        //TODO на мой взгляд, так будет более понятно что ли. И не придется в прошлое уходить, чтобы узнать,
        //TODO стоило ли производить обновление.
        //TODO Если ты считаешь, что так плохо, то можем обсудить.
        return update;
    }
}