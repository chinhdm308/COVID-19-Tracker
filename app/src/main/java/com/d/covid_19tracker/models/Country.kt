package com.d.covid_19tracker.models

class Country {
    private var updated: String? = null
    private var country: String? = null
    private var flag: String? = null
    private var cases: String? = null
    private var deaths: String? = null
    private var recovered: String? = null
    private var active: String? = null

    constructor()

    constructor(updated: String?, country: String?, flag: String?, cases: String?, deaths: String?, recovered: String?, active: String?) {
        this.updated = updated
        this.country = country
        this.flag = flag
        this.cases = cases
        this.deaths = deaths
        this.recovered = recovered
        this.active = active
    }

    fun getUpdated(): String? {
        return updated
    }

    fun setUpdate(updated: String?) {
        this.updated = updated
    }

    fun getCountry(): String? {
        return country
    }

    fun setCountry(country: String?) {
        this.country = country
    }

    fun getFlag(): String? {
        return flag
    }

    fun setFlag(flag: String?) {
        this.flag = flag
    }

    fun getCases(): String? {
        return cases
    }

    fun setCases(cases: String?) {
        this.cases = cases
    }

    fun getDeaths(): String? {
        return deaths
    }

    fun setDeaths(deaths: String?) {
        this.deaths = deaths
    }

    fun getRecovered(): String? {
        return recovered
    }

    fun setRecovered(recovered: String?) {
        this.recovered = recovered
    }

    fun getActive(): String? {
        return active
    }

    fun setActive(active: String?) {
        this.active = active
    }
}