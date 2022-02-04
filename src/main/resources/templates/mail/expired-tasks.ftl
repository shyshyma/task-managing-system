<#import "common.ftl" as c>
<@c.page style="warn">
    <table class="body-wrap">
        <tr>
            <td class="container">
                <table>
                    <tr>
                        <td align="center" class="masthead">
                            <h1>${header} &#10008;</h1>
                        </td>
                    </tr>
                    <tr>
                        <td class="content">
                            <h2>Dear ${name} ${surname} at the current moment you have not finished tasks
                                which have already expired:</h2>
                            <#list excludedTasks as excludedTask>
                                <p>- Title: ${excludedTask.title},
                                    date of
                                    expiration: ${excludedTask.expirationDate?date?string('dd/MM/yyyy HH:mm:ss')},
                                    done percentage: ${excludedTask.donePercentage},
                                    priority: ${excludedTask.priority},
                                    status: ${excludedTask.status}
                                </p>
                            </#list>
                            <table>
                                <tr>
                                    <td align="center">
                                        <p>
                                            <a href="#" class="button">Go to task list</a>
                                        </p>
                                    </td>
                                </tr>
                            </table>
                            <p>So please make this tasks with "Abandoned" status or "Finished" with 100% done
                                percentage.
                                Finally, if they not need for you, it's possible to delete them</p>
                            <p><em>– Yours support team ;)</em></p>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</@c.page>