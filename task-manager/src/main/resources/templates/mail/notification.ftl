<#import "common.ftl" as c>
<@c.page style="warn">
    <table class="body-wrap">
        <tr>
            <td class="container">
                <table>
                    <tr>
                        <td align="center" class="masthead">
                            <h1>Expired tasks &#10008;</h1>
                        </td>
                    </tr>
                    <tr>
                        <td class="content">
                            <h2>Dear ${name} ${surname}, please verify that you haven't expired tasks:</h2>
                            <table>
                                <tr>
                                    <td align="center">
                                        <p>
                                            <a href="#" class="button">Go to task list</a>
                                        </p>
                                    </td>
                                </tr>
                            </table>
                            <p>If you have them - make this tasks with "Abandoned" status or "Finished" with 100% done
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
