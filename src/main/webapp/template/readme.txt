******************************************************************************************************
*********************************** Suhan Dharmasuriya 16/01/2014 *************************************
******************************************************************************************************

template-rbr.zul      : is used after user logged in to the system to display the page links.
                        structure: - banner(Logo|Name|User|Logout)
                                   - sidebar(Page links)
                                   - footer

template-anonymous.zul: is used for null sessions/when user is not currently logged in to the system.
                        Only difference between this template and rbr template is the side bar is not present here.
                        structure: - banner(Logo|Name|User|Logout)
                                   - footer