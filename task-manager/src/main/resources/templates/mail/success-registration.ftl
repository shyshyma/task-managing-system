<#import "common.ftl" as c>
<@c.page style="success">
    <table class="body-wrap">
        <tr>
            <td class="container">
                <table>
                    <tr>
                        <td align="center" class="masthead">
                            <h1>Thanks for registration &#9825;</h1>
                        </td>
                    </tr>
                    <tr>
                        <td class="content">
                            <h2>Hello ${name} ${surname}, your registration to our service is approved.
                                To view your list of tasks please click here:</h2>
                            <table>
                                <tr>
                                    <td align="center">
                                        <p>
                                            <a href="#" class="button">Open task manager</a>
                                        </p>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
    </table>
</@c.page>
