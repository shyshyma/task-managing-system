<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <style>
        * {
            margin: 0;
            padding: 0;
            font-size: 100%;
            font-family: 'Avenir Next', "Helvetica Neue", "Helvetica", Helvetica, Arial, sans-serif;
            line-height: 1.65;
        }

        img {
            max-width: 100%;
            margin: 0 auto;
            display: block;
        }

        body,
        .body-wrap {
            width: 100% !important;
            height: 100%;
            background: #efefef;
            -webkit-font-smoothing: antialiased;
            -webkit-text-size-adjust: none;
        }

        a {
            color: #FF6666;
            text-decoration: none;
        }

        .button {
            display: inline-block;
            color: white;
            background: #FF6666;
            border: solid #FF6666;
            border-width: 10px 20px 8px;
            font-weight: bold;
            border-radius: 4px;
        }

        h1, h2, h3, h4, h5, h6 {
            margin-bottom: 20px;
            line-height: 1.25;
        }

        h1 {
            font-size: 32px;
        }

        h2 {
            font-size: 28px;
        }

        h3 {
            font-size: 24px;
        }

        h4 {
            font-size: 20px;
        }

        h5 {
            font-size: 16px;
        }

        p, ul, ol {
            font-size: 16px;
            font-weight: normal;
            margin-bottom: 20px;
        }

        .container {
            display: block !important;
            clear: both !important;
            margin: 0 auto !important;
            max-width: 580px !important;
        }

        .container table {
            width: 100% !important;
            border-collapse: collapse;
        }

        .container .masthead {
            padding: 80px 0;
            background: #FF6666;
            color: white;
        }

        .container .masthead h1 {
            margin: 0 auto !important;
            max-width: 90%;
            text-transform: uppercase;
        }

        .container .content {
            background: white;
            padding: 30px 35px;
        }

        .container .content.footer p {
            margin-bottom: 0;
            color: #888;
            text-align: center;
            font-size: 14px;
        }

        .container .content.footer a {
            color: #888;
            text-decoration: none;
            font-weight: bold;
        }

    </style>
</head>
<body>
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
                                date of expiration: ${excludedTask.expirationDate?date?string('dd/MM/yyyy HH:mm:ss')},
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
                        <p>So please make this tasks with "Abandoned" status or "Finished" with 100% done percentage.
                            Finally, if they not need for you, it's possible to delete them</p>
                        <p><em>– Yours support team ;)</em></p>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
</body>
</html>