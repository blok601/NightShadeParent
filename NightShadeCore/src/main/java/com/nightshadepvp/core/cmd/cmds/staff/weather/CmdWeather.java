package com.nightshadepvp.core.cmd.cmds.staff.weather;

import com.nightshadepvp.core.cmd.NightShadeCoreCommand;

import java.util.Collections;
import java.util.List;

/**
 * Created by Blok on 8/12/2019.
 */
public class CmdWeather extends NightShadeCoreCommand {

    private static CmdWeather i = new CmdWeather();

    public static CmdWeather get() {
        return i;
    }

    public CmdWeatherClear cmdWeatherClear = new CmdWeatherClear();

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("weather");
    }
}
